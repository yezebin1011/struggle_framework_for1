<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
   <head>
     <link href="plugin/main/css/main.css" rel="stylesheet" type="text/css" />
     <link href="plugin/extTree/css/ext-all.css" rel="stylesheet" type="text/css" />
     <script type="text/javascript" src="plugin/jquery/jquery-1.9.1.js"></script>
	 <script type="text/javascript" src="plugin/extTree/js/bootstrap.js"></script>
	 <script language="javascript">
	    var init = function(){
	    	
	    	document.getElementById("loginUserName").value = '${sessionScope.loginUserName}';
	    	
	    };
	 </script>
	 <script type="text/javascript" src="plugin/main/js/left.js"></script>
   </head>
   
   <body onload="init();">
     <div id="left">
        <input type="hidden" id="loginUserName"/>
        <div id="left_menu"></div>
        <div id="mainTree" style="border:1px solid #87CEFA;"></div>
     </div>
   </body>