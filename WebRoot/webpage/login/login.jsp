<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
   <head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>用户登录</title>
		<link href="plugin/login/css/login.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="plugin/jquery/jquery-1.9.1.js"></script>
		<script language="javascript">
		   $(function(){
			   $("#changeImage").click(function(){
			       $("#captchaImg").hide().attr("src","<c:url value='/jcaptcha'/>"+"?"+Math.floor(Math.random()*100)).fadeIn();
                });
			   
		   });
		</script>
		<script type="text/javascript" src="plugin/login/js/login.js"></script>
   </head>
   <body>
       
       <form id="loginForm" action="<c:url value='j_spring_security_check'/>" method="post">
	    <div id="login">
	    
		     <div id="top">
			      <div id="top_left"><img src="plugin/login/images/login_03.gif" /></div>
				  <div id="top_center"></div>
			 </div>
			 <div id="center">
			      <div id="center_left">
			      </div>
				  <div id="center_middle">
				       <div id="user">用 户
				         <input id="j_username" type="text" name="j_username"/>
				       </div>
					   <div id="password">密   码
					     <input id="j_password" type="password" name="j_password" /><br>
					   </div>
					   <div id="validatecode">验证码
					   <input id="j_validatecode" type="text" name="j_validatecode" style="width:50px;"/>
					   <img id="captchaImg" style= "vertical-align:middle" src= "<c:url value="/jcaptcha"/>" height="55px;" width="100px;"/><br/>
					   <a id="changeImage">看不清楚换一张</a>
					   <font color="red" id="errorMsg">
				            <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
				         </font>
					   </div>
					   <div id="btn">
					     <a id="toLoginCheck">登录</a><a id="toLoginClear">清空</a>
					   </div>
				  </div>
				  <div id="center_right"></div>		 
			 </div>
			 <div id="down">
			      <div id="down_left">
				      <div id="inf">
	                       <span class="inf_text">版本信息</span>
						   <span class="copyright">管理信息系统 2008 v2.0</span>
				      </div>
				  </div>
				  <div id="down_center"></div>		 
			 </div>
		  </div>
	  </form>
   </body>
</html>