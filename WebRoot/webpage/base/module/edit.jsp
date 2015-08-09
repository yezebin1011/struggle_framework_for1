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
      <script type="text/javascript" src="webpage/base/module/js/edit.js"></script>
      <script language="javascript">
         var init = function(){
        	 
        	 if('${module.module_isLeaf}' == '1'){
        		 
        		 $("#first_radio").attr('checked',true);
        		 
        	 }else{
        		 
        		 $("#second_radio").attr('checked',true);
        		 
        		 $("#urlDisplay").attr('style','display:none;');
        	 }
        	 
         };
      </script>
   </head>
   
   <body onload="init();">
      <div class="easyui-panel" title="模块信息修改" style="over-flow-x:auto;over-flow-y:auto;">
        <div style="padding: 5px 0 10px 30px">
           <form id="editForm" method="post">
              <input type="hidden" name="module.module_id" value="${module.module_id }"/>
              <table>
                 <tr>
                   <td>
                                                       模块名称：
                   </td>
                   <td>
                     ${module.module_name }
                   </td>
                 </tr>
                 <tr>
                    <td>
                                                       是否子节点:
                    </td>
                    <td>
                      <input type="radio"
                             id="first_radio"
                             name="module.module_isLeaf"
                             value="1"/>是
                      &nbsp;
                      <input type="radio"
                             id="second_radio"
                             class="easyui-validatebox"
                             validType= "radio['editForm','module.module_isLeaf']"
                             name="module.module_isLeaf"
                             value="0"/>否
                    </td>
                 </tr>
                 <tr id = "urlDisplay">
                    <td>
                                                         模块url:
                    </td>
                    <td>
                      <input type="text" 
	                          name="module.module_url" 
	                          value="${module.module_url }"
	                          style="width:200px;"
	                          />
                    </td>
                 </tr>
                 <tr>
                    <td>
                                                        上级模块：
                    </td>
                    <td>
                      <input id="module_parentId" 
                           value = "${module.module_parentId }"
                           class="easyui-combotree" 
                           data-options = "cascadeCheck:false"
                           style="width:200px;"
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