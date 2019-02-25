$(function(){
	
	//是子节点radio的点击事件
	$("#first_radio").click(function(){
		
		var count = $("input:radio[id='first_radio']:checked").length;
		
		if(count>0){
			
			$("#urlDisplay").attr('style','');
			
		}
		
	});
	
	//非子节点radio的点击事件
	$("#second_radio").click(function(){
		
		var count = $("input:radio[id='second_radio']:checked").length;
		
		if(count >0){
			
			$("#urlDisplay").attr('style','display:none;');
			
		}
		
	});
	
	//模块修改提交按钮
	$("#editSubmit").click(function(){
		
		$("#editForm").form('submit',{
			
			url : "module!editModule.action",
			
			onSubmit : function() {
				
				if (!$("#editForm").form('validate')) {
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
					
					window.parent.$("#moduleAdd").window('close');
					
					
				}else{
					
					$.messager.alert("提示",resultMsg);
				}
				
			}
		});
		
	});
	
	//模块修改清空按钮
	$("#editRest").click(function(){
		
		$("#editForm").form('reset');
		
	});
	
	//上级模块配置弹出窗
	$("#module_parentId").combotree({
		
		url : "module!getModuleQuerySelectList.action?module_id=-1",
		
		required : true
		
	});
	
});