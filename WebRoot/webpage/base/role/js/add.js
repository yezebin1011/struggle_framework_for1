$(function() {
	$("#addSubmit").click(function() {
		$("#addForm").form('submit', {
			url : "role!addRole.action",
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
						
						window.parent.$("#roleAdd").window('close');
						
						
					}else{
						
						$.messager.alert("提示",resultMsg);
					}
				
				
				
				}
			});
	
		});
	
	 
	 $("#addClear").click(function(){
		 
		 $("#addForm").form('clear');
		 
	 });
	
});