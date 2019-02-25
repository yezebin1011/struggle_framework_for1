$(function() {

	// 用户信息查询按钮
	$("#querySubmit").click(
			function() {
				var consumer_name = $($("input[name='consumer_name']")[0])
						.val();

				var consumer_realName = $(
						$("input[name='consumer_realName']")[0]).val();

				$("#dataGrid").datagrid('load', {
					consumer_name : consumer_name,
					consumer_realName : consumer_realName
				});

			});

	// 用户信息查询清除按钮
	$("#queryClear").click(function() {

		$("#queryForm").form('clear');

	});

	var rightContentFrame = window.top.document.getElementById("rightContent");

	var mainFrame = window.top.document.getElementById("mainFrame").contentWindow;

	// 用户信息查询列表
	$("#dataGrid")
			.datagrid(
					{
						title : "用户管理",
						url : "consumer!getConsumerInfoList.action",
						loadMsg : '数据装载中......',
						border : true,
						collapsible : false,
						idField : 'consumer_id',
						height : rightContentFrame.height
								- document.getElementById("querydiv").offsetHeight
								- mainFrame.document.getElementById("right_top").offsetHeight
								- document.getElementById("consumerAdd").offsetHeight,
						columns : [ [
								{
									field : "ck",
									checkbox : true
								},
								{
									field : "consumer_name",
									title : "用户姓名",
									sortable : true
								},
								{
									field : "consumer_realName",
									title : "真实姓名",
									sortable : true
								},
								{
									field : "opt",
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(value, rec) {
										return "<a id =\'"
												+ rec.consumer_id
												+ "\' class='openRoleConfig' style='color:red;cursor:pointer;'>分配角色</a>";
									}
								},
								{
									field : "opt1",
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(value, rec) {
										return "<a id =\'"
												+ rec.consumer_id
												+ "\' class='editPwd' style='color:red;cursor:pointer;'>修改密码</a>";
									}
								}
								] ],
						sortName : 't.consumer_id',
						sortOrder : 'desc',
						pagination : true,
						toolbar : [ {
							text : '新增',
							iconCls : 'icon-add',
							handler : openConsumerAddPage

						}, {
							text : '修改',
							iconCls : 'icon-edit',
							handler : openConsumerEditPage

						}, {
							text : '删除',
							iconCls : 'icon-remove',
							handler : deleteConsumer

						} ],
						onLoadSuccess : initHref

					});

	var p = $("#dataGrid").datagrid("getPager");

	$(p).pagination({

		pageSize : 10,// 每页显示的记录条数，默认为10

		pageList : [ 10, 20, 50, 100 ],// 可以设置每页记录条数的列表

		beforePageText : '第',

		afterPageText : '页， 共{pages}页',

		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录'

	});

});

// 用户新增弹出窗
var openConsumerAddPage = function() {

	var consumerAddIframe = $("<iframe>");

	consumerAddIframe.attr("id", "consumerAddIframe");

	consumerAddIframe.attr("src", "consumer!getConsumerAddPage.action");

	consumerAddIframe.attr("style", "width:100%;height:100%;");

	$("#consumerAdd").append(consumerAddIframe);

	$("#consumerAdd").window('open');

};

// 用户信息修改弹出窗
var openConsumerEditPage = function() {

	var selectItemCount = $("#dataGrid").datagrid("getSelections").length;

	if (selectItemCount == 0) {

		$.messager.alert("提示", "请选择需要修改的用户");

		return;

	} else if (selectItemCount > 1) {

		$.messager.alert("提示", "请选择一条需要修改的用户");

		return;
	}

	var selectItem = $("#dataGrid").datagrid("getSelected");

	var consumerEditIframe = $("<iframe>");

	consumerEditIframe.attr("id", "consumerEditIframe");

	consumerEditIframe.attr("src",
			"consumer!getConsumerEditPage.action?consumer_id="
					+ selectItem.consumer_id);

	consumerEditIframe.attr("style", "width:100%;height:100%;");

	$("#consumerAdd").append(consumerEditIframe);

	$("#consumerAdd").window('open');
};

// 用户删除
var deleteConsumer = function() {

	var selectedItem = $("#dataGrid").datagrid("getSelections");

	if (selectedItem.length == 0) {

		$.messager.alert("提示", "请选择需要删除的用户");

		return;

	}

	$.messager.confirm("删除提示", "确定要删除这些选中的用户吗？", function(r) {

		if (r) {

			var selectedIds = [];

			for ( var index = 0; index < selectedItem.length; index++) {

				selectedIds.push(selectedItem[index].consumer_id);
			}

			$("#checkedItem").val(selectedIds.join(","));

			$("#deleteForm").form('submit', {

				url : 'consumer!removeConsumer.action',

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

//用户配置角色弹出窗
var initHref = function() {

	$(".openRoleConfig").each(
			function() {

				$(this).click(
						function() {

							var roleConfigIframe = $("<iframe>");

							roleConfigIframe.attr("id", "roleConfig");

							var consumer_id = $(this).attr("id");

							roleConfigIframe.attr("src",
									"consumer!getRoleConfigPage.action?consumer_id="
											+ consumer_id);

							roleConfigIframe.attr("style",
									"width:100%;height:100%;");

							$("#roleConfig").append(roleConfigIframe);

							$("#roleConfig").window('open');

						});

			});
	
	$(".editPwd").each(function(){
			
			$(this).click(function(){
				
				var editPwdIframe = $("<iframe>");
				
				editPwdIframe.attr("id","editPwd");
				
				var consumer_id = $(this).attr("id");
				
				editPwdIframe.attr("src","consumer!getEditPwdPage.action?consumer_id="+consumer_id);
				
				editPwdIframe.attr("style","width:100%;height:100%;");
				
				$("#consumerAdd").append(editPwdIframe);
				
				$("#consumerAdd").window('open');
				
			});
			
		});

};

