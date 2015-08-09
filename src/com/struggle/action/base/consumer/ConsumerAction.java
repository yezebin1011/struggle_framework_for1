/**
 * 
 */
package com.struggle.action.base.consumer;

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
import com.struggle.management.base.consumer.manage.ConsumerManage;
import com.struggle.management.base.module.manage.ModuleManage;
import com.struggle.model.Consumer;
import com.struggle.model.Consumer_Role_Relation;
import com.struggle.model.Role;

/**
 * 用户信息管理控制类
 * 
 * @author yezebin
 * 
 */
@ParentPackage("struts-default")
@Namespace(value = "/")
@Action(value = "consumer!*")
@Results({
		@Result(name = "consumerList", location = "/webpage/base/consumer/list.jsp"),
		@Result(name = "consumerAdd", location = "/webpage/base/consumer/add.jsp"),
		@Result(name = "consumerEdit", location = "/webpage/base/consumer/edit.jsp"),
		@Result(name = "roleConfig", location = "/webpage/base/consumer/roleConfig.jsp"),
		@Result(name = "consumerEditPwd", location = "/webpage/base/consumer/editPwd.jsp") })
@Controller
public class ConsumerAction extends ActionSupport {

	private String rows;

	private String page;

	private String order;

	private String sort;

	private Consumer consumer;

	private Role role;

	private Consumer_Role_Relation consumer_role_rel;

	@Resource
	private ConsumerManage consumerManage;

	@Resource
	private ModuleManage moduleManage;

	/**
	 * 用户管理查询页面打开
	 * 
	 * @return
	 */
	public String consumerList() {

		HttpServletRequest request = ServletActionContext.getRequest();

		String module_id = request.getParameter("module_id");

		// 显示模块位置标题
		String moduleTitle = moduleManage.getModuleTitle(Long
				.parseLong(module_id));

		request.setAttribute("moduleTitle", moduleTitle);

		return "consumerList";

	}

	/**
	 * 用户信息查询
	 * 
	 * @throws IOException
	 */
	public void getConsumerInfoList() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();

		String consumer_name = request.getParameter("consumer_name");

		String consumer_realName = request.getParameter("consumer_realName");

		Map<String, String> condition = new HashMap<String, String>();

		if (StringUtils.isNotBlank(consumer_name)) {

			condition.put("consumer_name", consumer_name);

		}

		if (StringUtils.isNotBlank(consumer_realName)) {

			condition.put("consumer_realName", consumer_realName);
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

		JSONObject consumerListArray = null;

		try {

			consumerListArray = consumerManage
					.getConsumerInfoListArray(condition);

		} catch (Exception e) {

			e.printStackTrace();

		}

		System.out.println(consumerListArray);

		String consumerListArrayString = "";

		if (consumerListArray != null && !consumerListArray.isEmpty()) {

			consumerListArrayString = consumerListArray.toString();

		}

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setCharacterEncoding("utf-8");

		response.getWriter().write(consumerListArrayString);

	}

	/**
	 * 用户信息新增页面打开
	 * 
	 * @return
	 */
	public String getConsumerAddPage() {

		return "consumerAdd";

	}

	/**
	 * 用户信息修改页面打开
	 * 
	 * @return
	 */
	public String getConsumerEditPage() {

		HttpServletRequest request = ServletActionContext.getRequest();

		String consumer_id = request.getParameter("consumer_id");

		consumer = consumerManage.getById(consumer_id);

		return "consumerEdit";
	}

	/**
	 * 用户密码修改页面打开
	 * 
	 * @return
	 */
	public String getEditPwdPage() {

		HttpServletRequest request = ServletActionContext.getRequest();

		String consumer_id = request.getParameter("consumer_id");

		consumer = consumerManage.getById(consumer_id);

		return "consumerEditPwd";

	}

	/**
	 * 用户新增信息提交
	 * 
	 * @throws Exception
	 */
	public void addConsumer() throws Exception {

		JSONArray resultArray = null;

		Map<String, Object> resultMap = new HashMap<String, Object>();

		Long checkResult = consumerManage.getDuplicateConsumerName(consumer
				.getConsumer_name());

		if (checkResult > 0) {

			resultMap.put("result", false);

			resultMap.put("resultMsg", "用户名重复");

			resultArray = JSONArray.fromObject(resultMap);

		} else {

			try {

				resultArray = consumerManage.addConsumer(consumer);

			} catch (Exception e) {

				resultMap.put("result", false);

				resultMap.put("resultMsg", "用户新增失败");

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
	 * 用户修改信息提交
	 * 
	 * @throws Exception
	 */
	public void editConsumer() throws Exception {

		JSONArray resultArray = null;

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			resultArray = consumerManage.editConsumer(consumer);

		} catch (Exception e) {

			resultMap.put("result", false);

			resultMap.put("resultMsg", "用户修改失败");

			resultArray = JSONArray.fromObject(resultMap);

			e.printStackTrace();
		}

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/html;charset=utf-8");

		response.setCharacterEncoding("utf-8");

		response.getWriter().write(resultArray.toString());

	}

	/**
	 * 用户密码修改信息提交
	 * 
	 * @throws IOException
	 */
	public void editPwd() throws IOException {

		JSONArray resultArray = null;

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {

			resultArray = consumerManage.editPwd(consumer);

		} catch (Exception e) {

			resultMap.put("result", false);

			resultMap.put("resultMsg", "密码修改失败");

			resultArray = JSONArray.fromObject(resultMap);

			e.printStackTrace();

		}

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/html;charset=utf-8");

		response.setCharacterEncoding("utf-8");

		response.getWriter().write(resultArray.toString());

	}

	/**
	 * 用户信息删除
	 * 
	 * @throws Exception
	 */
	public void removeConsumer() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();

		String consumer_ids = request.getParameter("checkedItem");

		JSONArray resultArray = null;

		try {

			resultArray = consumerManage.removeConsumer(consumer_ids);

		} catch (Exception e) {

			Map<String, Object> resultMap = new HashMap<String, Object>();

			resultMap.put("result", false);

			resultMap.put("resultMsg", "用户信息删除失败");

			resultArray = JSONArray.fromObject(resultMap);

			e.printStackTrace();
		}

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/html;charset=utf-8");

		response.setCharacterEncoding("utf-8");

		response.getWriter().write(resultArray.toString());

	}

	/**
	 * 用户配置角色信息页面打开
	 * 
	 * @return
	 */
	public String getRoleConfigPage() {

		HttpServletRequest request = ServletActionContext.getRequest();

		String consumer_id = request.getParameter("consumer_id");

		consumer_role_rel = consumerManage.getByConsumerId(Long
				.parseLong(consumer_id));

		request.setAttribute("consumer_id", consumer_id);

		return "roleConfig";
	}

	/**
	 * 用户选择需要配置的角色信息树数据查询
	 * 
	 * @throws IOException
	 */
	public void getRoleConfigTreeInfo() throws IOException {

		String roleConfigJsonResultString = "";

		HttpServletRequest request = ServletActionContext.getRequest();

		String consumer_id = request.getParameter("consumer_id");

		JSONArray resultArray = null;

		resultArray = consumerManage.getConsumerRefRoleInfo(consumer_id);

		if (resultArray != null) {

			roleConfigJsonResultString = resultArray.toString();
		}

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setCharacterEncoding("utf-8");

		response.getWriter().write(roleConfigJsonResultString);

	}

	/**
	 * 用户配置角色信息提交
	 * 
	 * @throws IOException
	 */
	public void roleConfigSubmit() throws IOException {

		JSONArray jsonArray = null;

		String resultString = "";

		HttpServletRequest request = ServletActionContext.getRequest();

		String role_selected_id = request.getParameter("role_selected_id");

		Long consumer_id = Long.parseLong(request.getParameter("consumer_id"));

		String[] role_selected_stringArray = role_selected_id.split(",");

		Long[] role_selected_idArray = new Long[role_selected_stringArray.length];

		for (int index = 0; index < role_selected_stringArray.length; index++) {

			role_selected_idArray[index] = Long
					.parseLong(role_selected_stringArray[index]);
		}

		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("consumer_id", consumer_id);

		condition.put("role_selected_id", role_selected_idArray);

		condition.put("params", new Object[] { consumer_id });

		try {

			jsonArray = consumerManage.updateRoleConfig(condition);

		} catch (Exception e) {

			Map<String, Object> resultMap = new HashMap<String, Object>();

			resultMap.put("result", false);

			resultMap.put("resultMsg", "用户配置角色失败");

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

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public Consumer_Role_Relation getConsumer_role_rel() {
		return consumer_role_rel;
	}

	public void setConsumer_role_rel(Consumer_Role_Relation consumer_role_rel) {
		this.consumer_role_rel = consumer_role_rel;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
