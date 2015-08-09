$(function(){
	
	//角色信息查询按钮
	$("#querySubmit").click(function(){
		  
		var role_name = $($("input[name='role_name']")[0]).val();
		
		$("#dataGrid").datagrid('load', {
			role_name : role_name
		});
		
	});
	
	//角色信息查询清除按钮
	$("#queryClear").click(function() {

		$("#queryForm").form('clear');

	});
	
	var rightContentFrame = window.top.document.getElementById("rightContent");

	var mainFrame = window.top.document.getElementById("mainFrame").contentWindow;
	
	//角色信息列表
	$("#dataGrid").datagrid(
			{
				title : "角色管理",
				url : "role!getRoleInfoList.action",
				loadMsg : '数据装载中......',
				border : true,
				collapsible : false,
				idField : 'role_id',
				height : rightContentFrame.height
						- document.getElementById("querydiv").offsetHeight
						- mainFrame.document.getElementById("img").offsetHeight
						- document.getElementById("roleAdd").offsetHeight,
				columns : [ [ {
					field : "ck",
					checkbox : true
				},{
					field : "role_name",
					title : "角色名称",
					sortable : true
				},{
					field : "opt",
					title : '操作',
					width : 100,
					align : 'center',
					formatter : function(value, rec) {
						return "<a id =\'"
								+ rec.role_id
								+ "\' class='openModuleConfig' style='color:red;cursor:pointer;'>分配模块</a>";
					}
				}
				]],
				sortName : 't.role_id',
				sortOrder : 'desc',
				pagination : true,
				toolbar : [ {
					text : '新增',
					iconCls : 'icon-add',
					handler : openRoleAddPage

				}, {
					text : '删除',
					iconCls : 'icon-remove',
					handler : deleteRole

				} ],
				onLoadSuccess : openModuleConfig
			});

	var p = $("#dataGrid").datagrid("getPager");

	$(p).pagination({

		pageSize : 10,//每页显示的记录条数，默认为10  

		pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表

		beforePageText : '第',

		afterPageText : '页， 共{pages}页',

		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'

	});

 });

//角色新增弹出窗 
var openRoleAddPage = function(){
	 
	var consumerAddIframe = $("<iframe>");

	consumerAddIframe.attr("id", "roleAddIframe");

	consumerAddIframe.attr("src",
			"role!getRoleAddPage.action");

	consumerAddIframe.attr("style", "width:100%;height:100%;");

	$("#roleAdd").append(consumerAddIframe);

	$("#roleAdd").window('open');
	 
  };
  
 //角色信息删除
 var deleteRole = function(){
	 
	 var selectedItem = $("#dataGrid").datagrid("getSelections");
	 
		if (selectedItem.length == 0) {

			$.messager.alert("提示", "请选择需要删除的角色");
			
			return;

		}
		
		$.messager.confirm("删除提示","确定要删除这些选中的角色吗？",function(r){
			
			if(r){
				
				var selectedIds = [];

				for ( var index = 0; index < selectedItem.length; index++) {

					selectedIds.push(selectedItem[index].role_id);
				}

				$("#checkedItem").val(selectedIds.join(","));

				$("#deleteForm").form('submit', {

					url : 'role!removeRole.action',

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
 
 //角色配置模块弹出窗
 var openModuleConfig = function(){
	 
	 $(".openModuleConfig").each(function(i){
		 
		 $($(".openModuleConfig")[i]).click(function(){
			 
			 var moduleConfigIframe = $("<iframe>");

			 moduleConfigIframe.attr("id", "moduleConfig");

				var role_id = $(this).attr("id");
				
				moduleConfigIframe.attr("src",
						"role!getModuleConfigPage.action?role_id="
								+ role_id);

				moduleConfigIframe.attr("style",
						"width:100%;height:100%;");

				$("#moduleConfig").append(moduleConfigIframe);

				$("#moduleConfig").window('open');
			 
		 });
	 });
 };
