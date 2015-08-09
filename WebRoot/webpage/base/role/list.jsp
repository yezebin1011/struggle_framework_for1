<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
   <head>
      <link href="plugin/easyui/css/easyui.css" rel="stylesheet" type="text/css" />
      <link href="plugin/easyui/css/icon.css" rel="stylesheet" type="text/css" />
      <script type="text/javascript" src="plugin/jquery/jquery-1.9.1.js"></script>
	  <script type="text/javascript" src="plugin/easyui/js/jquery.easyui.min.js"></script>
	  <script type="text/javascript" src="plugin/easyui/js/jquery.easyui.messager.extend.js"></script>
	  <script type="text/javascript" src="plugin/main/js/right.js"></script>
	  <script type="text/javascript" src="webpage/base/role/js/list.js"></script>
   </head>
   
   <body style="margin:0px;">
     <div id="roleAdd" class="easyui-window" 
                           title="角色管理"
                           closed="true" 
                           style="width:600px;height:300px;"
                           data-options="onClose:function(){$('#roleAdd').empty();}">
     </div>
     <div id="moduleConfig" class="easyui-window" 
                           title="模块配置"
                           closed="true" 
                           style="width:450px;height:400px;"
                           data-options="onClose:function(){$('#moduleConfig').empty();}">
     </div>
     <div id="querydiv" class="easyui-panel" title="当前位置：${moduleTitle}">
        <div style="padding: 5px 0 10px 30px">
	      <form id="queryForm" method="post">
	         <table>
	            <tr>
	                <td>
	                                             角色名称：
	                </td>
	                <td>
	                  <input type="text" name="role_name"/>
	                </td>
	            </tr>
	         </table>
	      </form>
         </div>
         <div style="text-align:right;padding:5px;">
            <a id="querySubmit" class="easyui-linkbutton">查询</a>
            <a id="queryClear" class="easyui-linkbutton">清除</a>
         </div>
      </div>
      <form id="deleteForm" method="post">
         <input type="hidden" name="checkedItem" id="checkedItem"/>
	     <table id="dataGrid"></table>
	  </form>
   </body>

</html>   
