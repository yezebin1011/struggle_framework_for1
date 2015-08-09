
  $(function(){
	  
	 var consumer_id = $("#consumer_id").val();
	 
     //用户配置角色弹出窗
	 $("#consumer_role_config").combotree({
    	 
    	 url : "consumer!getRoleConfigTreeInfo.action?consumer_id="+consumer_id,
    	 
    	 required : true
    	 
     });
     
     //用户配置角色信息提交按钮
	 $("#configSubmit").click(function(){
    	 
    	 var consumer_role_config_id = $("#consumer_role_config").combotree('getValues');
    	 
    	 $("#role_selected_id").val(consumer_role_config_id);
    	 
    	 $("#roleConfigForm").form('submit',{
    		 
    		 url : 'consumer!roleConfigSubmit.action',
    		 
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
					
					window.parent.$("#roleConfig").window('close');
					
					
				}else{
					
					$.messager.alert("提示",resultMsg);
				}
 			 }
    		 
    	 });
    	 
    	 
     });
     
     //用户配置角色重置按钮
	 $("#configRest").click(function(){
    	 
    	  $("#configRest").form('reset');
     });
	  
  });