/**
 * 
 */
package com.struggle.action.core;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 系统登录安全控制类
 * 
 * @author yzb
 * 
 */
@ParentPackage("struts-default")
@Namespace(value = "/")
@Action(value = "springSecurityLogin!*")
@Results({ @Result(name = "login", location = "/webpage/login/login.jsp"),
		@Result(name = "timeout", location = "/webpage/common/timeout.jsp"),
		@Result(name = "refuse", location = "/webpage/common/refuse.jsp") })
@Controller
public class SpringSecurityLoginAction extends ActionSupport {

	public String doInit() {

		return "login";

	}

	public String doLoginTimeOut() {

		return "timeout";

	}

	public String doAuthorityDefiend() {

		return "refuse";
	}

}
