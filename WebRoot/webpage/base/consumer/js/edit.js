$(function() {
	
	//用户信息修改提交按钮
	$("#editSubmit").click(function() {
		$("#editForm").form('submit', {
			url : "consumer!editConsumer.action",
			onSubmit : function() {
				if (!$(this).form('validate')) {
					return false;
				}
			},
			success : function(data) {
				
					data = eval(data);
					
					var result = data[0].result;
					
					var resultMsg = data[0].resultMsg;
					
					if(result){
						
						window.parent.$.messager.alert("提示",resultMsg);
						
						window.parent.$("#dataGrid").datagrid('clearSelections');
						
						window.parent.$("#dataGrid").datagrid('reload');
						
						window.parent.$("#consumerAdd").window('close');
						
						
					}else{
						
						$.messager.alert("提示",resultMsg);
					}
				
				
				
				}
			});
	
		});
	
	 //用户信息重置按钮
	 $("#editRest").click(function(){
		 
		 $("#editForm").form('reset');
		 
	 });
	
});