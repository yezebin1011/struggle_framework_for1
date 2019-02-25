$(function() {

	$("#toLoginCheck").click(function() {

		loginSubmit();

	});

	$("input").keydown(function(e) {

		var curKey = e.which;

		if (curKey == 13) {

			$("#toLoginCheck").click();

			return false;
		}

	});

	$("#toLoginClear").click(function() {

		$("#j_username").val("");

		$("#j_password").val("");
		
		$("#j_validatecode").val("");

	});

});

var loginSubmit = function() {

	var username = $("#j_username").val();

	var password = $("#j_password").val();

	var errorMsg = $("#errorMsg");

	if (username == null || username == '') {

		errorMsg.empty();

		errorMsg.html("用户名不能为空");

		return false;

	}

	if (password == null || password == '') {

		errorMsg.empty();

		errorMsg.html("密码不能为空");

		return false;
	}

	$("#loginForm").submit();

};