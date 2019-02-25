<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
   <head>
      <title>管理信息系统</title>
   </head>
   
   <frameset rows="60,*" cols="*" frameborder="no"  framespacing="0">
      <frame src="login!doTop.action" name="topFrame" scrolling="no" noresize="noresize" id="topFrame"/>
      <frameset rows="*" cols="188,*" framespacing="0">
         <frame src="login!doLeft.action" name="leftFrame" scrolling="no" noresize="noresize" id="leftFrame" target="rightContent"/>
         <frameset rows="40,*" cols="*" framespacing="0">
            <frame src="login!doRightTop.action" name="mainFrame" id="mainFrame"/>
            <frame id="rightContent" name="rightContent" scrolling="no" src=""/>
         </frameset>
      </frameset>
   </frameset>
   <noframes>
      <body>
      </body>
   </noframes>
</html>