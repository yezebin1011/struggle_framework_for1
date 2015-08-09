/**
 * 
 */
package com.struggle.action.base.module;

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
import com.struggle.model.Module;

/**
 * 模块信息控制类
 * 
 * @author yzb
 * 
 */
@ParentPackage("struts-default")
@Namespace(value = "/")
@Action(value = "module!*")
@Results({
		@Result(name = "moduleInfoList", location = "/webpage/base/module/list.jsp"),
		@Result(name = "moduleAdd", location = "/webpage/base/module/add.jsp"),
		@Result(name = "moduleEdit", location = "/webpage/base/module/edit.jsp") })
@Controller
public class ModuleAction extends ActionSupport {

	private String rows;

	private String page;

	private String order;

	private String sort;

	private Module module;

	@Resource
	private ModuleManage moduleManage;

	/**
	 * 系统主界面左侧模块树节点点击后的查询
	 * 
	 * @throws IOException
	 */
	public void getChildModuleInfo() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();

		String module_id = request.getParameter("module_id");

		String consumer_name = request.getParameter("consumer_name");

		Map<String, String> condition = new HashMap<String, String>();

		condition.put("module_id", module_id);

		condition.put("consumer_name", consumer_name);

		JSONArray moduleListArray = moduleManage.getModuleListArray(condition);

		System.out.println(moduleListArray);

		String moduleListArrayString = "";

		if (!moduleListArray.isEmpty()) {

			moduleListArrayString = moduleListArray.toString();

		}

		System.out.println(moduleListArrayString);

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setCharacterEncoding("utf-8");

		response.getWriter().write(moduleListArrayString);

	}

	/**
	 * 模块信息管理主界面打开
	 * 
	 * @return
	 */
	public String moduleInfoList() {

		HttpServletRequest request = ServletActionContext.getRequest();

		String module_id = request.getParameter("module_id");

		// 显示模块位置标题
		String moduleTitle = moduleManage.getModuleTitle(Long
				.parseLong(module_id));

		request.setAttribute("moduleTitle", moduleTitle);

		return "moduleInfoList";
	}

	/**
	 * 模块信息查询
	 * 
	 * @throws IOException
	 */
	public void getModuleInfoList() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();

		String module_name = request.getParameter("module_name");

		String module_parentId = request.getParameter("module_parentId");

		Map<String, String> condition = new HashMap<String, String>();

		if (StringUtils.isNotBlank(module_name)) {

			condition.put("module_name", module_name);

		}

		if (StringUtils.isNotBlank(module_parentId)) {

			condition.put("module_parentId", module_parentId);
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

		JSONObject moduleListArray = null;

		try {

			moduleListArray = moduleManage.getModuleInfoListArray(condition);

		} catch (Exception e) {

			e.printStackTrace();
		}

		String moduleListArrayString = "";

		if (moduleListArray != null && !moduleListArray.isEmpty()) {

			moduleListArrayString = moduleListArray.toString();
		}

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setCharacterEncoding("utf-8");

		response.getWriter().write(moduleListArrayString);

	}

	/**
	 * 模块配置弹出窗打开后的模块树查询
	 * 
	 * @throws IOException
	 */
	public void getModuleQuerySelectList() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();

		String module_id = request.getParameter("module_id");

		Map<String, String> condition = new HashMap<String, String>();

		condition.put("module_id", module_id);

		JSONArray moduleQuerySelectListArray = moduleManage
				.getModuleQuerySelectList(condition);

		String moduleQuerySelectListArrayString = "";

		if (moduleQuerySelectListArray != null
				&& !moduleQuerySelectListArray.isEmpty()) {

			moduleQuerySelectListArrayString = moduleQuerySelectListArray
					.toString();
		}

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setCharacterEncoding("utf-8");

		response.getWriter().write(moduleQuerySelectListArrayString);

	}

	/**
	 * 模块信息新增页面打开
	 * 
	 * @return
	 */
	public String getModuleAddPage() {

		return "moduleAdd";
	}

	/**
	 * 模块新增
	 * 
	 * @throws IOException
	 */
	public void addModule() throws IOException {

		JSONArray resultArray = null;

		Map<String, Object> resultMap = new HashMap<String, Object>();

		Long checkResult = moduleManage.getDuplicateModuleName(module
				.getModule_name());

		if (checkResult > 0) {

			resultMap.put("result", false);

			resultMap.put("resultMsg", "用户名重复");

			resultArray = JSONArray.fromObject(resultMap);

		} else {

			try {

				resultArray = moduleManage.addModule(module);

			} catch (Exception e) {

				resultMap.put("result", false);

				resultMap.put("resultMsg", "模块新增失败");

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
	 * 模块修改页面打开
	 * 
	 * @return
	 */
	public String getModuleEditPage() {

		HttpServletRequest request = ServletActionContext.getRequest();

		String module_id = request.getParameter("module_id");

		module = moduleManage.getById(Long.parseLong(module_id));

		return "moduleEdit";

	}

	/**
	 * 模块修改
	 * 
	 * @throws IOException
	 */
	public void editModule() throws IOException {

		JSONArray resultArray = null;

		String resultArrayString = "";

		try {

			resultArray = moduleManage.editModule(module);

		} catch (Exception e) {

			Map<String, Object> resultMap = new HashMap<String, Object>();

			resultMap.put("result", false);

			resultMap.put("resultMsg", "模块修改失败");

			resultArray = JSONArray.fromObject(resultMap);

			e.printStackTrace();
		}

		if (resultArray != null && !resultArray.isEmpty()) {

			resultArrayString = resultArray.toString();
		}

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/html;charset=utf-8");

		response.setCharacterEncoding("utf-8");

		response.getWriter().write(resultArrayString);

	}

	/**
	 * 模块删除
	 * 
	 * @throws IOException
	 */
	public void removeModule() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();

		String module_ids = request.getParameter("checkedItem");

		JSONArray resultArray = null;

		try {

			resultArray = moduleManage.removeModule(module_ids);

		} catch (Exception e) {

			Map<String, Object> resultMap = new HashMap<String, Object>();

			resultMap.put("result", false);

			resultMap.put("resultMsg", "模块信息删除失败");

			resultArray = JSONArray.fromObject(resultMap);

			e.printStackTrace();
		}

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/html;charset=utf-8");

		response.setCharacterEncoding("utf-8");

		response.getWriter().write(resultArray.toString());

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

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

}
