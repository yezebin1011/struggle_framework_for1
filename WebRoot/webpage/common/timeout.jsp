<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <body>
                  长时间未操作，已经超时！<br>
                 点击该链接重新登录：<a href="<c:url value='j_spring_security_logout'/>" target="_top">注销</a>
   </body>
</html>