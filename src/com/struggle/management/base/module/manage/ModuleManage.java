/**
 * 
 */
package com.struggle.management.base.module.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.struggle.management.base.module.dao.ModuleDao;
import com.struggle.model.Module;
import com.struggle.vo.TreeHasChildNode;
import com.struggle.vo.TreeNode;

/**
 * 模块信息管理类
 * 
 * @author yzb
 * 
 */
@Service
public class ModuleManage {

	@Resource
	private ModuleDao moduleDao;

	/**
	 * 系统主界面左侧模块树，节点点击后的查询
	 * 
	 * @param condition
	 * @return
	 */
	public JSONArray getModuleListArray(Map<String, String> condition) {

		List<Module> moduleList = new ArrayList<Module>();

		moduleList = moduleDao.getModuleList(condition);

		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

		for (Module module : moduleList) {

			TreeNode treeNode = new TreeNode();

			treeNode.setId(String.valueOf(module.getModule_id()));

			treeNode.setText(module.getModule_name());

			if (module.getModule_isLeaf() == 1) {

				treeNode.setLeaf(true);

			} else {

				treeNode.setLeaf(false);
			}

			treeNode.setExpand(true);

			if (StringUtils.isNotBlank(module.getModule_url())) {

				if (StringUtils.contains(module.getModule_url(), "?")) {

					treeNode.setHref(module.getModule_url() + "&module_id="
							+ module.getModule_id());

				} else {

					treeNode.setHref(module.getModule_url() + "?module_id="
							+ module.getModule_id());
				}

			} else {

				treeNode.setHref(module.getModule_url());
			}

			treeNode.setHrefTarget("rightContent");

			treeNodeList.add(treeNode);

		}

		JSONArray moduleListArray = JSONArray.fromObject(treeNodeList);

		return moduleListArray;

	}

	/**
	 * 模块信息管理主界面查询
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public JSONObject getModuleInfoListArray(Map<String, String> condition)
			throws Exception {

		Map<String, Object> moduleInfoJsonMap = new HashMap<String, Object>();

		String mainSql = " from Module t1,Module t2";

		mainSql += " where t2.module_id = t1.module_parentId ";

		String querySql = " select new com.struggle.vo.ModuleInfoList(t1.module_id,t1.module_name,t2.module_name)"
				+ mainSql;

		if (condition.containsKey("module_name")
				&& StringUtils.isNotBlank(condition.get("module_name"))) {

			querySql += " and t1.module_name like '%"
					+ condition.get("module_name") + "%'";
		}

		if (condition.containsKey("module_parentId")
				&& StringUtils.isNotBlank(condition.get("module_parentId"))) {

			querySql += " and t1.module_parentId = "
					+ condition.get("module_parentId");
		}

		if (condition.containsKey("sort")
				&& StringUtils.isNotBlank(condition.get("sort"))) {

			String sortName = "";

			if (StringUtils.equals("module_name", condition.get("sort"))) {

				sortName = " t1." + condition.get("sort");

			} else if (StringUtils.equals("module_parentName",
					condition.get("sort"))) {

				sortName = " t2.module_name";

			} else {

				sortName = condition.get("sort");
			}

			querySql += " order by " + sortName;
		}

		if (condition.containsKey("order")
				&& StringUtils.isNotBlank(condition.get("order"))) {

			querySql += " " + condition.get("order");
		}

		condition.put("queryListInfoSql", querySql);

		String queryListCountSql = "select count(*) " + mainSql;

		condition.put("queryListCountSql", queryListCountSql);

		List<Module> list = new ArrayList<Module>();

		list = moduleDao.getModuleInfoList(condition);

		long total = 0;

		total = moduleDao.getModuleInfoListCount(condition);

		moduleInfoJsonMap.put("total", total);

		moduleInfoJsonMap.put("rows", list);

		JSONObject moduleListArray = JSONObject.fromObject(moduleInfoJsonMap);

		return moduleListArray;

	}

	/**
	 * 模块配置页面弹出窗，模块树数据查询
	 * 
	 * @param condition
	 * @return
	 */
	public JSONArray getModuleQuerySelectList(Map<String, String> condition) {

		List<Module> moduleList = new ArrayList<Module>();

		moduleList = moduleDao.getModuleSelectList(condition);

		List<Object> treeNodeList = new ArrayList<Object>();

		for (Module module : moduleList) {

			getAllModuleInfoTreeInfo(module, treeNodeList);

		}

		JSONArray moduleListArray = JSONArray.fromObject(treeNodeList);

		return moduleListArray;

	}

	/**
	 * 模块新增，模块名称是否重复的校验
	 * 
	 * @param module_name
	 * @return
	 */
	public Long getDuplicateModuleName(String module_name) {

		Map<String, String> condition = new HashMap<String, String>();

		String queryListCountSql = "select count(*) from Module t";

		if (StringUtils.isNotBlank(module_name)) {

			queryListCountSql += " where";

			queryListCountSql += " t.module_name = '" + module_name + "'";

		}

		condition.put("queryListCountSql", queryListCountSql);

		return moduleDao.getModuleInfoListCount(condition);
	}

	/**
	 * 模块新增
	 * 
	 * @param module
	 * @return
	 * @throws Exception
	 */
	public JSONArray addModule(Module module) throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (module.getModule_parentId() == null) {

			module.setModule_parentId(new Long(0));
		}

		moduleDao.insertModule(module);

		resultMap.put("result", true);

		resultMap.put("resultMsg", "用户新增成功");

		JSONArray resultArray = JSONArray.fromObject(resultMap);

		return resultArray;

	}

	/**
	 * 模块修改
	 * 
	 * @param module
	 * @return
	 * @throws Exception
	 */
	public JSONArray editModule(Module module) throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		Module sourceModule = moduleDao.getById(module.getModule_id());

		sourceModule.setModule_isLeaf(module.getModule_isLeaf());

		sourceModule.setModule_url(module.getModule_url());

		sourceModule.setModule_parentId(module.getModule_parentId());

		moduleDao.updateModule(sourceModule);

		resultMap.put("result", true);

		resultMap.put("resultMsg", "模块信息修改成功");

		JSONArray resultArray = JSONArray.fromObject(resultMap);

		return resultArray;

	}

	public Module getById(Long module_id) {

		return moduleDao.getById(module_id);

	}

	/**
	 * 模块删除
	 * 
	 * @param checkedItem
	 * @return
	 * @throws Exception
	 */
	public JSONArray removeModule(String checkedItem) throws Exception {

		JSONArray resultArray = null;

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String[] module_id_array = checkedItem.split(",");

		Map<String, String> checkMap = new HashMap<String, String>();

		// 校验模块是否已经分配了角色
		for (String module_id : module_id_array) {

			String queryListCountSql = " select count(*) "
					+ " from Role_Module_Relation t1,Module t2"
					+ " where t1.role_module_relationId.module_id = t2.module_id "
					+ " and t1.role_module_relationId.module_id = ";

			queryListCountSql += module_id;

			checkMap.put("queryListCountSql", queryListCountSql);

			Long checkResult = moduleDao.getModuleInfoListCount(checkMap);

			if (checkResult > 0) {

				resultMap.put("result", true);

				resultMap.put("resultMsg", "模块已经配置角色，无法删除，模块代号为：" + module_id);

				resultArray = JSONArray.fromObject(resultMap);

				return resultArray;

			}

		}

		Object[] params = new Object[module_id_array.length];

		for (int index = 0; index < module_id_array.length; index++) {

			params[index] = Long.parseLong(module_id_array[index]);

		}

		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("params", params);

		String querySql = "delete from Module t where t.module_id in (:module_id)";

		condition.put("querySql", querySql);

		moduleDao.deleteModule(condition);

		resultMap.put("result", true);

		resultMap.put("resultMsg", "模块信息删除成功");

		resultArray = JSONArray.fromObject(resultMap);

		return resultArray;

	}

	/**
	 * 管理页面上方-模块位置信息显示
	 * 
	 * @param module_id
	 * @return
	 */
	public String getModuleTitle(Long module_id) {

		String modulTitle = "";

		Module module = moduleDao.getById(module_id);

		if (module != null) {

			modulTitle += module.getModule_name();

			modulTitle = getModuleByParentId(modulTitle,
					module.getModule_parentId());

		}

		return modulTitle;

	}

	private void getAllModuleInfoTreeInfo(Module treeModule,
			List<Object> treeNodeList) {

		String id = String.valueOf(treeModule.getModule_id());

		String text = treeModule.getModule_name();

		String state = "open";

		Boolean checked = false;

		Map<String, String> condition = new HashMap<String, String>();

		condition.put("module_id", id);

		List<Module> childModuleList = new ArrayList<Module>();

		childModuleList = moduleDao.getModuleSelectList(condition);

		if (!childModuleList.isEmpty()) {

			TreeHasChildNode treeHasChildNode = new TreeHasChildNode();

			treeHasChildNode.setId(id);

			treeHasChildNode.setText(text);

			treeHasChildNode.setState(state);

			treeHasChildNode.setChecked(checked);

			List<Object> treeHasChildNodeList = new ArrayList<Object>();

			for (Module childModule : childModuleList) {

				getAllModuleInfoTreeInfo(childModule, treeHasChildNodeList);

			}

			treeHasChildNode.setChildren(treeHasChildNodeList);

			treeNodeList.add(treeHasChildNode);

		} else {

			TreeNode treeHasNoChildNode = new TreeNode();

			treeHasNoChildNode.setId(id);

			treeHasNoChildNode.setText(text);

			treeHasNoChildNode.setState(state);

			treeHasNoChildNode.setChecked(checked);

			treeNodeList.add(treeHasNoChildNode);

		}

	}

	private String getModuleByParentId(String moduleTitle, Long parentId) {

		Module tempObj = moduleDao.getById(parentId);

		if (tempObj != null) {

			moduleTitle = tempObj.getModule_name() + " -- " + moduleTitle;

			Module parentTempObj = moduleDao.getById(tempObj
					.getModule_parentId());

			if (parentTempObj != null) {

				moduleTitle = parentTempObj.getModule_name() + " -- "
						+ moduleTitle;

			}

			moduleTitle = getModuleByParentId(moduleTitle,
					parentTempObj.getModule_parentId());

		}

		return moduleTitle;
	}

}
