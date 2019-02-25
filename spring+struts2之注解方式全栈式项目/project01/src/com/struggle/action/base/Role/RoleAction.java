/**
 * 
 */
package com.struggle.action.base.Role;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.struggle.management.base.module.manage.ModuleManage;
import com.struggle.management.base.role.manage.RoleManage;
import com.struggle.model.Role;
import com.struggle.model.Role_Module_Relation;

/**
 * 角色信息控制类
 * 
 * @author yezebin
 * 
 */
@ParentPackage("struts-default")
@Namespace(value = "/")
@Action(value = "role!*")
@Results({
		@Result(name = "roleInfoList", location = "/webpage/base/role/list.jsp"),
		@Result(name = "roleInfoAdd", location = "/webpage/base/role/add.jsp"),
		@Result(name = "moduleConfig", location = "/webpage/base/role/moduleConfig.jsp") })
@Controller
public class RoleAction extends ActionSupport {

	private String rows;

	private String page;

	private String order;

	private String sort;

	private Role role;

	private Role_Module_Relation role_module_rel;

	@Resource
	private RoleManage roleManage;

	@Resource
	private ModuleManage moduleManage;

	/**
	 * 角色管理页面打开
	 * 
	 * @return
	 */
	public String roleInfoList() {

		HttpServletRequest request = ServletActionContext.getRequest();

		String module_id = request.getParameter("module_id");

		// 显示模块位置标题
		String moduleTitle = moduleManage.getModuleTitle(Long
				.parseLong(module_id));

		request.setAttribute("moduleTitle", moduleTitle);

		return "roleInfoList";

	}

	/**
	 * 角色信息查询
	 * 
	 * @throws IOException
	 */
	public void getRoleInfoList() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();

		String role_name = request.getParameter("role_name");

		Map<String, String> condition = new HashMap<String, String>();

		if (StringUtils.isNotBlank(role_name)) {

			condition.put("role_name", role_name);

		}

		int intPage = Integer.parseInt((page == null || page == "0") ? "1"
				: page);

		int number = Integer.parseInt((rows == null || rows == "0") ? "1"
				: rows);

		int start = (intPage - 1) * number;

		condition.put("number", String.valueOf(number));

		condition.put("start", String.valueOf(start));

		condition.put("order", order);

		condition.put("sort", sort);

		JSONObject roleInfoListArray = null;

		try {

			roleInfoListArray = roleManage.getRoleInfoListArray(condition);

		} catch (Exception e) {

			e.printStackTrace();
		}

		System.out.println(roleInfoListArray);

		String roleInfoListArrayString = "";

		if (roleInfoListArray != null && !roleInfoListArray.isEmpty()) {

			roleInfoListArrayString = roleInfoListArray.toString();

		}

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setCharacterEncoding("utf-8");

		response.getWriter().write(roleInfoListArrayString);

	}

	/**
	 * 角色信息新增页面打开
	 * 
	 * @return
	 */
	public String getRoleAddPage() {

		return "roleInfoAdd";

	}

	/**
	 * 角色新增
	 * 
	 * @throws IOException
	 */
	public void addRole() throws IOException {

		JSONArray resultArray = null;

		Map<String, Object> resultMap = new HashMap<String, Object>();

		Long checkResult = roleManage.getDuplicateRoleName(role.getRole_name());

		if (checkResult > 0) {

			resultMap.put("result", false);

			resultMap.put("resultMsg", "角色名重复");

			resultArray = JSONArray.fromObject(resultMap);

		} else {

			try {

				resultArray = roleManage.addRole(role);

			} catch (Exception e) {

				resultMap.put("result", false);

				resultMap.put("resultMsg", "角色新增失败");

				resultArray = JSONArray.fromObject(resultMap);

				e.printStackTrace();

			}
		}

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/html;charset=utf-8");

		response.setCharacterEncoding("utf-8");

		response.getWriter().write(resultArray.toString());

	}

	/**
	 * 角色删除
	 * 
	 * @throws IOException
	 */
	public void removeRole() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();

		String role_ids = request.getParameter("checkedItem");

		JSONArray resultArray = null;

		try {

			resultArray = roleManage.removeRole(role_ids);

		} catch (Exception e) {

			Map<String, Object> resultMap = new HashMap<String, Object>();

			resultMap.put("result", false);

			resultMap.put("resultMsg", "角色信息删除失败");

			resultArray = JSONArray.fromObject(resultMap);

			e.printStackTrace();
		}

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/html;charset=utf-8");

		response.setCharacterEncoding("utf-8");

		response.getWriter().write(resultArray.toString());

	}

	/**
	 * 角色配置模块页面打开
	 * 
	 * @return
	 */
	public String getModuleConfigPage() {

		HttpServletRequest request = ServletActionContext.getRequest();

		String role_id = request.getParameter("role_id");

		String role_module_configInfo = roleManage.getModuleconfigInfo(Long
				.parseLong(role_id));

		request.setAttribute("role_id", role_id);

		request.setAttribute("role_module_configInfo", role_module_configInfo);

		return "moduleConfig";
	}

	/**
	 * 角色配置模块信息提交
	 * 
	 * @throws IOException
	 */
	public void moduleConfigSubmit() throws IOException {

		JSONArray jsonArray = null;

		String resultString = "";

		HttpServletRequest request = ServletActionContext.getRequest();

		String module_selected_id = request.getParameter("module_selected_id");

		Long role_id = Long.parseLong(request.getParameter("role_id"));

		String[] module_selected_stringArray = module_selected_id.split(",");

		Long[] module_selected_idArray = new Long[module_selected_stringArray.length];

		for (int index = 0; index < module_selected_stringArray.length; index++) {

			module_selected_idArray[index] = Long
					.parseLong(module_selected_stringArray[index]);
		}

		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("role_id", role_id);

		condition.put("module_selected_id", module_selected_idArray);

		condition.put("params", new Object[] { role_id });

		try {

			jsonArray = roleManage.updateModuleConfig(condition);

		} catch (Exception e) {

			Map<String, Object> resultMap = new HashMap<String, Object>();

			resultMap.put("result", false);

			resultMap.put("resultMsg", "角色配置模块失败");

			jsonArray = JSONArray.fromObject(resultMap);

			e.printStackTrace();
		}

		if (jsonArray != null) {

			resultString = jsonArray.toString();
		}

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/html;charset=utf-8");

		response.setCharacterEncoding("utf-8");

		response.getWriter().write(resultString);
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public RoleManage getRoleManage() {
		return roleManage;
	}

	public void setRoleManage(RoleManage roleManage) {
		this.roleManage = roleManage;
	}

	public Role_Module_Relation getRole_module_rel() {
		return role_module_rel;
	}

	public void setRole_module_rel(Role_Module_Relation role_module_rel) {
		this.role_module_rel = role_module_rel;
	}

}
