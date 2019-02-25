<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>   
<html>
   <body>
                  该请求已被拒绝！<br>
                 点击该链接返回登录页面：<a href="<c:url value='j_spring_security_logout'/>" target="_top">注销</a>
   </body>
</html>