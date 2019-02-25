
 $(function(){
	 
	 //角色配置模块信息提交按钮
	 $("#configSubmit").click(function(){
		 
         var role_module_config_id = $("#role_module_config").combotree('getValues');
    	 
    	 $("#module_selected_id").val(role_module_config_id);
    	 
    	 $("#moduleConfigForm").form('submit',{
    		 
    		 url: "role!moduleConfigSubmit.action",
    		 
    		 onSubmit : function() {
  				if (!$(this).form('validate')) {
  					return false;
  				}
  			 },
  			 
  			 success : function(data){
  				 
                data = eval(data);
				
				var result = data[0].result;
				
				var resultMsg = data[0].resultMsg;
				
				if(result){
					
					window.parent.$.messager.alert("提示",resultMsg);
					
					window.parent.$("#dataGrid").datagrid('clearSelections');
					
					window.parent.$("#dataGrid").datagrid('reload');
					
					window.parent.$("#moduleConfig").window('close');
					
					
				}else{
					
					$.messager.alert("提示",resultMsg);
				}
  			 }
    	 });
	 });
	 
	 //角色配置模块重置按钮
	 $("#configRest").click(function(){
		 
		 $("#moduleConfigForm").form('reset');
		 
	 });
	 
 });
 
