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
      <script type="text/javascript" src="plugin/easyui/js/jquery.easyui.validatebox.rules.extend.js"></script>
      <script type="text/javascript" src="plugin/easyui/js/jquery.easyui.messager.extend.js"></script>
      <script type="text/javascript" src="webpage/base/consumer/js/edit.js"></script>
   </head>
   
   <body>
      <div class="easyui-panel" title="用户信息修改" style="over-flow-x:auto;over-flow-y:auto;">
         <div style="padding: 5px 0 10px 30px">
	         <form id="editForm" method="post">
	            <input type="hidden" name="consumer.consumer_id" value="${consumer.consumer_id}"/>
	            <table>
	               <tr>
	                 <td>
	                                                  用户名：
	                 </td>
	                 <td>
	                   <input type="hidden" name="consumer.consumer_name" value="${consumer.consumer_name}"/>
	                   ${consumer.consumer_name }
	                 </td>
	               </tr>
	               <tr>
	                 <td>
	                                                  真实姓名：
	                 </td>
	                 <td>
	                   <input type="text" 
	                          class="easyui-validatebox" 
	                          name="consumer.consumer_realName"
	                          value="${consumer.consumer_realName}"
	                          required = "required"
	                          missingMessage = "请输入真实姓名"
	                          />
	                 </td>
	               </tr>
	            </table>
	          </form>
          </div>
          <div style="text-align:center;padding:5px;">
            <a id="editSubmit" class="easyui-linkbutton">确定</a>
            <a id="editRest" class="easyui-linkbutton">重置</a>
          </div>
      </div>
   
   </body>

</html>