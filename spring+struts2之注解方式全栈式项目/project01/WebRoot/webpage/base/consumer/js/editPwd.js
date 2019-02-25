
 $(function(){
	 
	 //用户密码修改提交按钮
	 $("#editSubmit").click(function(){
		 
		 $("#editForm").form('submit',{
			 
			 url : "consumer!editPwd.action",
			 
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
	 
     //用户密码清除按钮
	 $("#editClear").click(function(){
		 
		 $("#editForm").form('clear');
		 
	 });
	 
 });