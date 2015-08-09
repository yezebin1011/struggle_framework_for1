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
	  <script type="text/javascript" src="webpage/base/consumer/js/roleConfig.js"></script>
   </head>
   
   <body>
      <div class="easyui-panel" title="角色配置" style="over-flow-x:auto;over-flow-y:auto;">
         <div style="padding: 5px 0 10px 30px">
            <form id="roleConfigForm" method="post">
               <input type="hidden" id="consumer_id" name="consumer_id" value="${consumer_id }"/>
               <input type="hidden" id="role_selected_id" name="role_selected_id"/>
               <table>
                  <tr>
                     <td>
                                                             已配置的角色：
                     </td>
                     <td>
                       <select id="consumer_role_config"
                               value="${consumer_role_rel.consumer_role_relationId.role_id }"
		                       class="easyui-combotree" 
		                       name="language" 
		                       data-options="cascadeCheck:false" 
		                       missingMessage = "配置角色不能为空"
		                       multiple style="width:200px;">
		               </select>
                     </td>
                  </tr>
               </table>
            </form>
         </div>
         <div style="text-align:center;padding:5px;">
            <a id="configSubmit" class="easyui-linkbutton">确定</a>
            <a id="configRest" class="easyui-linkbutton">重置</a>
         </div>
      </div>
   </body>
</html>