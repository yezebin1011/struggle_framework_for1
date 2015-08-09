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
      <script type="text/javascript" src="webpage/base/consumer/js/editPwd.js"></script>
  </head>
  
  <body>
    <div class="easyui-panel" title="用户密码修改" style="over-flow-x:auto;over-flow-y:auto;">
       <div style="padding: 5px 0 10px 30px">
          <form id="editForm" method="post">
             <input type="hidden" name="consumer.consumer_id" value="${consumer.consumer_id}"/>
             <table>
                <tr>
                 <td>
                                                  新密码：
                 </td>
                 <td>
                   <input type="password" 
                          class="easyui-validatebox" 
                          id = "consumer_pwd"
                          name="consumer.consumer_pwd" 
                             required = "required"
                             missingMessage = "密码不能为空"
                             />
                 </td>
               </tr>
               <tr>
                 <td>
                                                  重复密码：
                 </td>
                 <td>
                   <input type="password" 
                          class="easyui-validatebox" 
                          name="repassord"
                          required = "required"
                          missingMessage = "请重新输入密码"
                          validType = "repassword['#consumer_pwd']" 
                          />
                 </td>
               </tr>
             </table>
          </form>
       </div>
       <div style="text-align:center;padding:5px;">
          <a id="editSubmit" class="easyui-linkbutton">确定</a>
          <a id="editClear" class="easyui-linkbutton">清除</a>
       </div>
    </div>
  </body>
  


</html>