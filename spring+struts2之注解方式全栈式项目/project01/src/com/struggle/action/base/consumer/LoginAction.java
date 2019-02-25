/**
 * 
 */
package com.struggle.action.base.consumer;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 系统登录控制类
 * 
 * @author yzb
 * 
 */
@ParentPackage("struts-default")
@Namespace(value = "/")
@Action(value = "login!*")
@Results({ @Result(name = "main", location = "/webpage/main/main.jsp"),
		@Result(name = "login", location = "/webpage/login/login.jsp"),
		@Result(name = "left", location = "/webpage/main/left.jsp"),
		@Result(name = "top", location = "/webpage/main/top.jsp"),
		@Result(name = "rightTop", location = "/webpage/main/right_top.jsp"),
		@Result(name = "right", location = "/webpage/main/right.jsp") })
@Controller
public class LoginAction extends ActionSupport {

	/**
	 * 用户登录
	 * 
	 * @return
	 */
	public String doLogin() {

		HttpSession session = ServletActionContext.getRequest().getSession();

		User loadUser = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		session.setAttribute("loginUserName", loadUser.getUsername());

		return "main";

	}

	/**
	 * 用户登录失败后的处理
	 * 
	 * @return
	 */
	public String doFailure() {

		HttpSession session = ServletActionContext.getRequest().getSession();

		System.out.println(((Exception) session
				.getAttribute("SPRING_SECURITY_LAST_EXCEPTION")).getMessage());

		return "login";

	}

	/**
	 * 用户注销
	 * 
	 * @return
	 */
	public String doLoginOut() {

		return "login";
	}

	public String doLeft() {

		return "left";
	}

	public String doTop() {

		return "top";
	}

	public String doRightTop() {

		return "rightTop";
	}

	public String doRight() {

		return "right";
	}

}
