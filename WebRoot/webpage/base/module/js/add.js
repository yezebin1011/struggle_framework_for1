$(function(){
	
	//是子节点radio的点击事件
	$("#first_radio").click(function(){
		
		var count = $("input:radio[id='first_radio']:checked").length;
		
		if(count>0){
			
			$("#urlDisplay").attr('style','display:block;');
			
		}
		
	});
	
	//非子节点radio的点击事件
	$("#second_radio").click(function(){
		
		var count = $("input:radio[id='second_radio']:checked").length;
		
		if(count >0){
			
			$("#urlDisplay").attr('style','display:none;');
			
			$("#module_parentId").attr('data-options','required:true,cascadeCheck:false');
		}
		
	});
	
	//模块新增提交按钮
	$("#addSubmit").click(function(){
		
		$("#addForm").form('submit',{
			
			url : "module!addModule.action",
			
			onSubmit : function() {
				
				if (!$("#addForm").form('validate')) {
					return false;
				}
				
				$("#module_parentId").val($("#module_parentId").combotree('getValue'));
				
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
	
	//模块新增清空按钮
	$("#addClear").click(function(){
		
		$("#addForm").form('clear');
		
	});
	
	//模块新增配置
	$("#module_parentId").combotree({
		
		url : "module!getModuleQuerySelectList.action?module_id=-1",
		
		required : true
		
	});
	
	
});