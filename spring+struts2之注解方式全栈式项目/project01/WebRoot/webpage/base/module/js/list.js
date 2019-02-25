
   $(function(){
	   
	   //模块信息查询按钮
	   $("#querySubmit").click(
				function() {
					var module_name = $($("input[name='module_name']")[0])
							.val();

					var module_parentId = $("#module_parentId").combotree('getValue');

					$("#dataGrid").datagrid('load', {
						module_name : module_name,
						module_parentId : module_parentId
					});

				});

		//模块信息查询清除按钮
	   $("#queryClear").click(function() {

			$("#queryForm").form('clear');

		});
		
		var rightContentFrame = window.top.document.getElementById("rightContent");

		var mainFrame = window.top.document.getElementById("mainFrame").contentWindow;
		
		//模块信息列表
		$("#dataGrid").datagrid(
				{
					title : "模块管理",
					url : "module!getModuleInfoList.action",
					loadMsg : '数据装载中......',
					border : true,
					collapsible : false,
					idField : 'module_id',
					height : rightContentFrame.height
							- document.getElementById("querydiv").offsetHeight
							- mainFrame.document.getElementById("img").offsetHeight
							- document.getElementById("moduleAdd").offsetHeight,
					columns : [ [ {
						field : "ck",
						checkbox : true
					}, {
						field : "module_name",
						title : "模块名称",
						sortable : true
					}, {
						field : "module_parentName",
						title : "上级模块",
						sortable : true
					} ] ],
					sortName : 't1.module_id',
					sortOrder : 'desc',
					pagination : true,
					toolbar : [ {
						text : '新增',
						iconCls : 'icon-add',
						handler : openModuleAddPage

					}, {
						text : '修改',
						iconCls : 'icon-edit',
						handler : openModuleEditPage

					}, {
						text : '删除',
						iconCls : 'icon-remove',
						handler : deleteModule

					} ]
						
				});
		
		var p = $("#dataGrid").datagrid("getPager");

		$(p).pagination({

			pageSize : 10,//每页显示的记录条数，默认为10  

			pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表

			beforePageText : '第',

			afterPageText : '页， 共{pages}页',

			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'

		});
		
	    //模块信息查询-上级模块信息查询条件选择弹出窗
		$("#module_parentId").combotree({
			
			url : "module!getModuleQuerySelectList.action?module_id=-1"
			
		});
		
   });
   
    //模块信息新增弹出窗
    var openModuleAddPage = function(){
    	
    	var moduleAddIframe = $("<iframe>");

    	moduleAddIframe.attr("id", "moduleAddIframe");

    	moduleAddIframe.attr("src", "module!getModuleAddPage.action");

    	moduleAddIframe.attr("style", "width:100%;height:100%;");

    	$("#moduleAdd").append(moduleAddIframe);

    	$("#moduleAdd").window('open'); 
    	
    };
    
    //模块信息修改弹出窗
    var openModuleEditPage = function(){
    	
    	var selectItemCount = $("#dataGrid").datagrid("getSelections").length;

    	if (selectItemCount == 0) {

    		$.messager.alert("提示", "请选择需要修改的模块");

    		return;

    	} else if (selectItemCount > 1) {

    		$.messager.alert("提示", "请选择一条需要修改的模块");

    		return;
    	}

    	var selectItem = $("#dataGrid").datagrid("getSelected");
    	
    	var moduleEditIframe = $("<iframe>");

    	moduleEditIframe.attr("id", "moduleEditIframe");

    	moduleEditIframe.attr("src",
    			"module!getModuleEditPage.action?module_id="
    					+ selectItem.module_id);

    	moduleEditIframe.attr("style", "width:100%;height:100%;");

    	$("#moduleAdd").append(moduleEditIframe);

    	$("#moduleAdd").window('open');
    	
    	
    };
    
    //模块信息删除
    var deleteModule = function(){
    	
    	var selectedItem = $("#dataGrid").datagrid("getSelections");

    	if (selectedItem.length == 0) {

    		$.messager.alert("提示", "请选择需要删除的模块");

    		return;

    	}
    	
    	$.messager.confirm("删除提示","确定要删除这些模块吗？",function(r){
    		
    		if(r){
    			
    			var selectedIds = [];

    			for ( var index = 0; index < selectedItem.length; index++) {

    				selectedIds.push(selectedItem[index].module_id);
    			}

    			$("#checkedItem").val(selectedIds.join(","));
    			
    			$("#deleteForm").form('submit', {

    				url : 'module!removeModule.action',

    				success : function(data) {

    					data = eval(data);

    					var result = data[0].result;

    					var resultMsg = data[0].resultMsg;

    					if (result) {

    						$.messager.alert("提示", resultMsg);

    						$("#dataGrid").datagrid('clearSelections');

    						$("#dataGrid").datagrid('reload');

    					} else {

    						$.messager.alert("提示", resultMsg);
    					}

    				}

    			});
    		}
    		
    	});
    	
    };