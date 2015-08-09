<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
   <head>
      <link href="plugin/main/css/main.css" rel="stylesheet" type="text/css" />
   </head>
   
   <body>
     <div id="top">
      <div id="logo"></div>
      <div id="user">欢迎您: <font color="blue">${sessionScope.loginUserName}</font></div>
     </div>
   </body>
  
</html>