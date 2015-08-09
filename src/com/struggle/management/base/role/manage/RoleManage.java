/**
 * 
 */
package com.struggle.management.base.role.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.struggle.management.base.role.dao.RoleDao;
import com.struggle.model.Module;
import com.struggle.model.Role;
import com.struggle.model.Role_Module_Relation;
import com.struggle.model.Role_Module_RelationId;

/**
 * 角色信息管理类
 * 
 * @author yezebin
 * 
 */
@Service
public class RoleManage {

	@Resource
	private RoleDao roleDao;

	/**
	 * 角色信息管理主界面查询
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public JSONObject getRoleInfoListArray(Map<String, String> condition)
			throws Exception {

		Map<String, Object> roleInfoJsonMap = new HashMap<String, Object>();

		String querySql = " from Role t";

		querySql += " where 1=1";

		if (condition.containsKey("role_name")
				&& StringUtils.isNotBlank(condition.get("role_name"))) {

			querySql += " and t.role_name like '%" + condition.get("role_name")
					+ "%'";

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

		List<Role> list = new ArrayList<Role>();

		list = roleDao.getRoleInfoList(condition);

		long total = 0;

		total = roleDao.getRoleInfoListCount(condition);

		roleInfoJsonMap.put("total", total);

		roleInfoJsonMap.put("rows", list);

		JSONObject roleInfoListArray = JSONObject.fromObject(roleInfoJsonMap);

		return roleInfoListArray;
	}

	/**
	 * 角色新增，角色名称是否重复的校验
	 * 
	 * @param role_name
	 * @return
	 */
	public Long getDuplicateRoleName(String role_name) {

		Map<String, String> condition = new HashMap<String, String>();

		String queryListCountSql = "select count(*) from Role t";

		if (StringUtils.isNotBlank(role_name)) {

			queryListCountSql += " where";

			queryListCountSql += " t.role_name = '" + role_name + "'";

		}

		condition.put("queryListCountSql", queryListCountSql);

		return roleDao.getRoleInfoListCount(condition);

	}

	/**
	 * 角色新增
	 * 
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public JSONArray addRole(Role role) throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		roleDao.insertRole(role);

		resultMap.put("result", true);

		resultMap.put("resultMsg", "角色新增成功");

		JSONArray resultArray = JSONArray.fromObject(resultMap);

		return resultArray;

	}

	public JSONArray editRole(Role role) throws Exception {

		return null;

	}

	/**
	 * 角色删除
	 * 
	 * @param checkedItem
	 * @return
	 * @throws Exception
	 */
	public JSONArray removeRole(String checkedItem) throws Exception {

		JSONArray resultArray = null;

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String[] role_id_array = checkedItem.split(",");

		Map<String, String> checkMap = new HashMap<String, String>();

		for (String role_id : role_id_array) {

			// 校验角色是否配置了用户
			String queryConsumerConfigSql = "select count(*) from Consumer_Role_Relation t1,Role t2"
					+ " where t1.consumer_role_relationId.role_id = t2.role_id"
					+ " and t2.role_id=" + role_id;

			checkMap.put("queryListCountSql", queryConsumerConfigSql);

			Long checkConsumerConfigCount = roleDao
					.getRoleInfoListCount(checkMap);

			if (checkConsumerConfigCount > 0) {

				resultMap.put("result", true);

				resultMap.put("resultMsg", "角色已经配置用户，无法删除，角色代号为：" + role_id);

				resultArray = JSONArray.fromObject(resultMap);

				return resultArray;
			}

			// 校验角色是否配置了模块
			String queryModuleConfigSql = "select count(*) from Role_Module_Relation t1,Role t2"
					+ " where t1.role_module_relationId.role_id = t2.role_id"
					+ " and t2.role_id =" + role_id;

			checkMap.put("queryListCountSql", queryModuleConfigSql);

			Long checkModuleConfigCount = roleDao
					.getRoleInfoListCount(checkMap);

			if (checkModuleConfigCount > 0) {

				resultMap.put("result", true);

				resultMap.put("resultMsg", "角色已经配置模块，无法删除，角色代号为：" + role_id);

				resultArray = JSONArray.fromObject(resultMap);

				return resultArray;

			}

		}

		Object[] params = new Object[role_id_array.length];

		for (int index = 0; index < role_id_array.length; index++) {

			params[index] = Long.parseLong(role_id_array[index]);

		}

		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("params", params);

		String querySql = "delete from Role t where t.role_id in (:role_id)";

		condition.put("querySql", querySql);

		roleDao.deleteRole(condition);

		resultMap.put("result", true);

		resultMap.put("resultMsg", "角色信息删除成功");

		resultArray = JSONArray.fromObject(resultMap);

		return resultArray;
	}

	/**
	 * 角色配置模块页面打开，获取用户已经配置的模块信息
	 * 
	 * @param role_id
	 * @return
	 */
	public String getModuleconfigInfo(Long role_id) {

		String role_module_configInfo = "";

		List<Role_Module_Relation> list = new ArrayList<Role_Module_Relation>();

		list = roleDao.getByRoleId(role_id);

		if (!list.isEmpty()) {

			for (int index = 0; index < list.size(); index++) {

				Role_Module_Relation role_module_rel = list.get(index);

				String module_id = String.valueOf(role_module_rel
						.getRole_module_relationId().getModule_id());

				role_module_configInfo += module_id;

				if ((list.size() == 1 && index == 0)
						|| (list.size() > 1 && index == (list.size() - 1))) {

					continue;

				} else {

					role_module_configInfo += ",";
				}

			}
		}

		return role_module_configInfo;

	}

	/**
	 * 角色配置模块信息提交
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public JSONArray updateModuleConfig(Map<String, Object> condition)
			throws Exception {

		JSONArray jsonArray = null;

		// 获取角色原来配置的模块信息
		List<Module> checkedModuleList = new ArrayList<Module>();

		String queryCheckedSql = " select new Module(t2.module_id,t2.module_name) "
				+ " from Role_Module_Relation t1,Module t2"
				+ " where t1.role_module_relationId.module_id = t2.module_id "
				+ " and t1.role_module_relationId.role_id = ?";

		condition.put("querySql", queryCheckedSql);

		checkedModuleList = roleDao.getModuleConfigInfo(condition);

		Long[] module_id_array = (Long[]) condition.get("module_selected_id");

		Long role_id = (Long) condition.get("role_id");

		List<Role_Module_Relation> addList = new ArrayList<Role_Module_Relation>();

		List<Role_Module_Relation> removeList = new ArrayList<Role_Module_Relation>();

		if (!checkedModuleList.isEmpty()) {

			// 获取需要新增的模块信息
			for (Long module_selected_id : module_id_array) {

				boolean needInsert = true;

				for (Module module : checkedModuleList) {

					if (module_selected_id.equals(module.getModule_id())) {

						needInsert = false;

						break;
					}

				}

				if (needInsert) {

					Role_Module_RelationId addId = new Role_Module_RelationId();

					addId.setRole_id(role_id);

					addId.setModule_id(module_selected_id);

					Role_Module_Relation role_module_relation_add = new Role_Module_Relation();

					role_module_relation_add.setRole_module_relationId(addId);

					addList.add(role_module_relation_add);

				}

			}

			// 获取需要删除的模块信息
			for (Module module : checkedModuleList) {

				boolean needRemove = true;

				for (Long module_selected_id : module_id_array) {

					if (module.getModule_id().equals(module_selected_id)) {

						needRemove = false;

						break;
					}
				}

				if (needRemove) {

					Role_Module_RelationId removeId = new Role_Module_RelationId();

					removeId.setRole_id(role_id);

					removeId.setModule_id(module.getModule_id());

					Role_Module_Relation role_module_relation = new Role_Module_Relation();

					role_module_relation.setRole_module_relationId(removeId);

					removeList.add(role_module_relation);
				}
			}

		} else {// 没有角色原有配置的模块信息，则将选中的模块信息全部作为新增的模块信息

			for (Long module_selected_id : module_id_array) {

				Role_Module_RelationId addId = new Role_Module_RelationId();

				addId.setRole_id(role_id);

				addId.setModule_id(module_selected_id);

				Role_Module_Relation role_module_relation_add = new Role_Module_Relation();

				role_module_relation_add.setRole_module_relationId(addId);

				addList.add(role_module_relation_add);

			}

		}

		// 新增模块
		roleDao.insertRole_Module_Relation(addList);

		// 删除模块
		roleDao.deleteRole_Module_Relation(removeList);

		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("result", true);

		resultMap.put("resultMsg", "角色配置模块信息成功");

		jsonArray = JSONArray.fromObject(resultMap);

		return jsonArray;

	}

}
