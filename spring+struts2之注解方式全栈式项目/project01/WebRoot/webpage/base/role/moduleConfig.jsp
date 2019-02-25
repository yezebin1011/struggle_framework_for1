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
	  <script language="javascript">
	  
	     var init = function(){

	         $("#role_module_config").combotree({
			 
				 url : "module!getModuleQuerySelectList.action?module_id=-1",
				 
				 required : true,

				 cascadeCheck:false
			 
		     });
	    	
	    	 var module_selected_id = '${role_module_configInfo}';

	         if(module_selected_id != null && module_selected_id != ""){

	    		 var module_selected_id_array = module_selected_id.split(",");

	    		 $("#role_module_config").combotree('setValues',module_selected_id_array); 
	    		 
	     	 }
	    	 
	     };
	  </script>
	  <script type="text/javascript" src="webpage/base/role/js/moduleConfig.js" charset="gb2312"></script>
  </head>
   
  <body onload="init();">
    <div class="easyui-panel" title="模块配置" style="over-flow-x:auto;over-flow-y:auto;">
       <div style="padding: 5px 0 10px 30px">
          <form id="moduleConfigForm" method="post">
             <input type="hidden" id="role_id" name="role_id" value="${role_id }"/>
               <input type="hidden" id="module_selected_id" name="module_selected_id"/>
               <table>
                  <tr>
                     <td>
                                                             已配置的模块：
                     </td>
                     <td>
                       <select id="role_module_config"
		                       class="easyui-combotree" 
		                       name="language" 
		                       multiple
		                       missingMessage = "配置模块不能为空"
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