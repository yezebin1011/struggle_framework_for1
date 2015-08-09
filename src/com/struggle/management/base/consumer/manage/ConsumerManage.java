/**
 * 
 */
package com.struggle.management.base.consumer.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.struggle.management.base.consumer.dao.ConsumerDao;
import com.struggle.model.Consumer;
import com.struggle.model.Consumer_Role_Relation;
import com.struggle.model.Consumer_Role_RelationId;
import com.struggle.model.Role;
import com.struggle.vo.TreeNode;

/**
 * 用户信息管理类
 * 
 * @author yzb
 * 
 */
@Service
public class ConsumerManage {

	@Resource
	private ConsumerDao consumerDao;

	/**
	 * 用户登录信息校验查询
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<Consumer> getConsumerLogin(Map<String, String> condition)
			throws Exception {

		Map<String, Object> queryCondition = new HashMap<String, Object>();

		String consumer_name = condition.get("consumer_name");

		String consumer_pwd = condition.get("consumer_pwd");

		String querySql = "from Consumer t";

		Object[] params = null;

		if (StringUtils.isNotBlank(consumer_name)) {

			querySql += " where";

			if (StringUtils.isNotBlank(consumer_name)) {

				querySql += " t.consumer_name = ?";

			}

			if (StringUtils.isNotBlank(consumer_pwd)) {

				querySql += " and t.consumer_pwd = ?";

				params = new Object[2];

				params[0] = consumer_name;

				params[1] = consumer_pwd;

			} else {

				params = new Object[1];

				params[0] = consumer_name;
			}
		}
		queryCondition.put("querySql", querySql);

		queryCondition.put("params", params);

		return consumerDao.getConsumerInfo(queryCondition);

	}

	/**
	 * 用户信息管理主界面查询
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public JSONObject getConsumerInfoListArray(Map<String, String> condition)
			throws Exception {

		Map<String, Object> consumerInfoJsonMap = new HashMap<String, Object>();

		String querySql = " from Consumer t";

		querySql += " where 1=1";

		if (condition.containsKey("consumer_name")
				&& StringUtils.isNotBlank(condition.get("consumer_name"))) {

			querySql += " and t.consumer_name like '%"
					+ condition.get("consumer_name") + "%'";

		}

		if (condition.containsKey("consumer_realName")
				&& StringUtils.isNotBlank(condition.get("consumer_realName"))) {

			querySql += " and t.consumer_realName like '%"
					+ condition.get("consumer_realName") + "%'";

		}

		if (condition.containsKey("sort")
				&& StringUtils.isNotBlank(condition.get("sort"))) {

			querySql += " order by " + condition.get("sort");
		}

		if (condition.containsKey("order")
				&& StringUtils.isNotBlank(condition.get("order"))) {

			querySql += " " + condition.get("order");
		}

		condition.put("queryListInfoSql", querySql);

		String queryListCountSql = "select count(*) " + querySql;

		condition.put("queryListCountSql", queryListCountSql);

		List<Consumer> list = new ArrayList<Consumer>();

		list = consumerDao.getConsumerInfoList(condition);

		long total = 0;

		total = consumerDao.getConsumerInfoListCount(condition);

		consumerInfoJsonMap.put("total", total);

		consumerInfoJsonMap.put("rows", list);

		JSONObject consumerListArray = JSONObject
				.fromObject(consumerInfoJsonMap);

		return consumerListArray;

	}

	/**
	 * 用户新增是否用户名重复校验
	 * 
	 * @param consumer_name
	 * @return
	 * @throws Exception
	 */
	public Long getDuplicateConsumerName(String consumer_name) throws Exception {

		Map<String, String> condition = new HashMap<String, String>();

		String queryListCountSql = "select count(*) from Consumer t";

		if (StringUtils.isNotBlank(consumer_name)) {

			queryListCountSql += " where";

			queryListCountSql += " t.consumer_name = '" + consumer_name + "'";

		}

		condition.put("queryListCountSql", queryListCountSql);

		return consumerDao.getConsumerInfoListCount(condition);

	}

	/**
	 * 用户新增
	 * 
	 * @param consumer
	 * @return
	 * @throws Exception
	 */
	public JSONArray addConsumer(Consumer consumer) throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		consumerDao.insertConsumer(consumer);

		resultMap.put("result", true);

		resultMap.put("resultMsg", "用户新增成功");

		JSONArray resultArray = JSONArray.fromObject(resultMap);

		return resultArray;

	}

	/**
	 * 用户修改
	 * 
	 * @param consumer
	 * @return
	 * @throws Exception
	 */
	public JSONArray editConsumer(Consumer consumer) throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		Consumer sourceConsumer = consumerDao
				.getById(consumer.getConsumer_id());

		sourceConsumer.setConsumer_realName(consumer.getConsumer_realName());

		consumerDao.updateConsumer(sourceConsumer);

		resultMap.put("result", true);

		resultMap.put("resultMsg", "用户修改成功");

		JSONArray resultArray = JSONArray.fromObject(resultMap);

		return resultArray;

	}

	/**
	 * 用户密码修改
	 * 
	 * @param consumer
	 * @return
	 * @throws Exception
	 */
	public JSONArray editPwd(Consumer consumer) throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		Consumer sourceConsumer = consumerDao
				.getById(consumer.getConsumer_id());

		sourceConsumer.setConsumer_pwd(consumer.getConsumer_pwd());

		consumerDao.updateConsumer(sourceConsumer);

		resultMap.put("result", true);

		resultMap.put("resultMsg", "密码修改成功");

		JSONArray resultArray = JSONArray.fromObject(resultMap);

		return resultArray;
	}

	public Consumer getById(String consumer_id) {

		return consumerDao.getById(Long.parseLong(consumer_id));
	}

	/**
	 * 用户删除
	 * 
	 * @param checkedItem
	 * @return
	 * @throws Exception
	 */
	public JSONArray removeConsumer(String checkedItem) throws Exception {

		JSONArray resultArray = null;

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String[] consumer_id_array = checkedItem.split(",");

		Map<String, String> checkMap = new HashMap<String, String>();

		// 校验用户是否配置了角色信息
		for (String consumer_id : consumer_id_array) {

			String queryListCountSql = " select count(*) "
					+ " from Consumer_Role_Relation t1,Role t2"
					+ " where t1.consumer_role_relationId.role_id = t2.role_id "
					+ " and t1.consumer_role_relationId.consumer_id = ";

			queryListCountSql += consumer_id;

			checkMap.put("queryListCountSql", queryListCountSql);

			Long checkResult = consumerDao.getConsumerInfoListCount(checkMap);

			if (checkResult > 0) {

				resultMap.put("result", true);

				resultMap
						.put("resultMsg", "用户已经配置角色，无法删除，用户代号为：" + consumer_id);

				resultArray = JSONArray.fromObject(resultMap);

				return resultArray;

			}

		}

		Object[] params = new Object[consumer_id_array.length];

		for (int index = 0; index < consumer_id_array.length; index++) {

			params[index] = Long.parseLong(consumer_id_array[index]);

		}

		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("params", params);

		String querySql = "delete from Consumer t where t.consumer_id in (:consumer_id)";

		condition.put("querySql", querySql);

		consumerDao.deleteConsumer(condition);

		resultMap.put("result", true);

		resultMap.put("resultMsg", "用户信息删除成功");

		resultArray = JSONArray.fromObject(resultMap);

		return resultArray;

	}

	/**
	 * 用户配置角色页面打开，角色树数据查询
	 * 
	 * @param consumer_id
	 * @return
	 */
	public JSONArray getConsumerRefRoleInfo(String consumer_id) {

		JSONArray resultArray = null;

		Map<String, Object> condition = new HashMap<String, Object>();

		String queryAllSql = " select new Role(t1.role_id,t1.role_name) from Role t1 ";

		String queryCheckedSql = " select new Role(t2.role_id,t2.role_name) "
				+ " from Consumer_Role_Relation t1,Role t2"
				+ " where t1.consumer_role_relationId.role_id = t2.role_id "
				+ " and t1.consumer_role_relationId.consumer_id = ?";

		Object[] params = { Long.parseLong(consumer_id) };

		condition.put("querySql", queryAllSql);

		// 获取所有角色信息
		List<Role> allRoleList = new ArrayList<Role>();

		allRoleList = consumerDao.getRoleConfigInfo(condition);

		// 获取用户已经被选择的角色信息
		List<Role> checkedRoleList = new ArrayList<Role>();

		condition.put("querySql", queryCheckedSql);

		condition.put("params", params);

		checkedRoleList = consumerDao.getRoleConfigInfo(condition);

		resultArray = getRoleConfigArray(allRoleList, checkedRoleList);

		return resultArray;

	}

	public Consumer_Role_Relation getByConsumerId(Long consumer_id) {

		return consumerDao.getByConsumerId(consumer_id);

	}

	/**
	 * 用户配置角色信息提交
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public JSONArray updateRoleConfig(Map<String, Object> condition)
			throws Exception {

		JSONArray jsonArray = null;

		// 获取用户原来配置的角色信息
		List<Role> checkedRoleList = new ArrayList<Role>();

		String queryCheckedSql = " select new Role(t2.role_id,t2.role_name) "
				+ " from Consumer_Role_Relation t1,Role t2"
				+ " where t1.consumer_role_relationId.role_id = t2.role_id "
				+ " and t1.consumer_role_relationId.consumer_id = ?";

		condition.put("querySql", queryCheckedSql);

		checkedRoleList = consumerDao.getRoleConfigInfo(condition);

		Long[] role_id_array = (Long[]) condition.get("role_selected_id");

		Long consumer_id = (Long) condition.get("consumer_id");

		List<Consumer_Role_Relation> addList = new ArrayList<Consumer_Role_Relation>();

		List<Consumer_Role_Relation> removeList = new ArrayList<Consumer_Role_Relation>();

		if (!checkedRoleList.isEmpty()) {

			// 获取需要新增的角色信息
			for (Long role_selected_id : role_id_array) {

				boolean needInsert = true;

				for (Role role : checkedRoleList) {

					if (role_selected_id.equals(role.getRole_id())) {

						needInsert = false;

						break;
					}

				}

				if (needInsert) {

					Consumer_Role_RelationId addId = new Consumer_Role_RelationId();

					addId.setConsumer_id(consumer_id);

					addId.setRole_id(role_selected_id);

					Consumer_Role_Relation consumer_role_relation_add = new Consumer_Role_Relation();

					consumer_role_relation_add
							.setConsumer_role_relationId(addId);

					addList.add(consumer_role_relation_add);

				}

			}

			// 获取需要删除的角色信息
			for (Role role : checkedRoleList) {

				boolean needRemove = true;

				for (Long role_selected_id : role_id_array) {

					if (role.getRole_id().equals(role_selected_id)) {

						needRemove = false;

						break;
					}

				}

				if (needRemove) {

					Consumer_Role_RelationId removeId = new Consumer_Role_RelationId();

					removeId.setConsumer_id(consumer_id);

					removeId.setRole_id(role.getRole_id());

					Consumer_Role_Relation consumer_role_relation_remove = new Consumer_Role_Relation();

					consumer_role_relation_remove
							.setConsumer_role_relationId(removeId);

					removeList.add(consumer_role_relation_remove);

				}

			}

		} else {// 没有用户原有配置的角色信息，则将选中的角色信息全部作为新增的角色信息

			for (Long role_selected_id : role_id_array) {

				Consumer_Role_RelationId addId = new Consumer_Role_RelationId();

				addId.setConsumer_id(consumer_id);

				addId.setRole_id(role_selected_id);

				Consumer_Role_Relation consumer_role_relation_add = new Consumer_Role_Relation();

				consumer_role_relation_add.setConsumer_role_relationId(addId);

				addList.add(consumer_role_relation_add);

			}

		}

		// 新增角色信息
		consumerDao.insertConsumer_Role_RelForBatch(addList);

		// 删除角色信息
		consumerDao.deleteConsumer_Role_RelForBatch(removeList);

		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("result", true);

		resultMap.put("resultMsg", "用户配置角色信息成功");

		jsonArray = JSONArray.fromObject(resultMap);

		return jsonArray;

	}

	private JSONArray getRoleConfigArray(List<Role> allRoleList,
			List<Role> checkedRoleList) {

		JSONArray jsonArray = null;

		if (allRoleList.isEmpty())
			return jsonArray;

		List<TreeNode> roleConfigTreeList = new ArrayList<TreeNode>();

		if (checkedRoleList.isEmpty()) {// 对应用户没有配置角色的情况

			for (Role allRole : allRoleList) {

				TreeNode treeNode = new TreeNode();

				treeNode.setId(String.valueOf(allRole.getRole_id()));

				treeNode.setText(allRole.getRole_name());

				treeNode.setChecked(false);

				treeNode.setState("open");

				roleConfigTreeList.add(treeNode);
			}

		} else {

			for (Role allRole : allRoleList) {

				TreeNode treeNode = new TreeNode();

				treeNode.setId(String.valueOf(allRole.getRole_id()));

				treeNode.setText(allRole.getRole_name());

				treeNode.setState("open");

				boolean isChecked = false;

				for (Role checkedRole : checkedRoleList) {

					if (allRole.getRole_id().equals(checkedRole.getRole_id())) {

						isChecked = true;

						break;
					}
				}

				if (isChecked) {

					isChecked = true;

				} else {

					isChecked = false;
				}

				treeNode.setChecked(isChecked);

				roleConfigTreeList.add(treeNode);
			}

		}

		if (roleConfigTreeList.isEmpty())
			return jsonArray;

		jsonArray = JSONArray.fromObject(roleConfigTreeList);

		return jsonArray;

	}

}
