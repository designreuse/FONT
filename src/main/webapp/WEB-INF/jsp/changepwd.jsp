<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge;charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>专题管理</title>

<link href="${spath }/content/css/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${spath}/content/css/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="${spath}/content/css/plugins/iCheck/custom.css"
	rel="stylesheet">
<link href="${spath}/content/css/plugins/steps/jquery.steps.css"
	rel="stylesheet">
<!-- Toastr style -->
<link href="${spath}/content/js/acms/toastr/toastr.min.css"
	rel="stylesheet">
<link href="${spath }/content/css/acms/animate.css" rel="stylesheet">
<link href="${spath }/content/css/acms/style.css" rel="stylesheet">
<link href="${spath }/content/css/acms/style.min.css" rel="stylesheet">

<!-- Main -->
<script type="text/javascript"
	src="${spath}/content/js/jquery/jquery-2.1.1.js"></script>
<script type="text/javascript"
	src="${spath}/content/js/jquery/jquery.md5.js"></script>
<script type="text/javascript"
	src="${spath}/content/css/bootstrap/js/bootstrap.min.js"></script>

<!-- Mainly scripts -->
<script src="${spath}/content/js/acms/metisMenu/jquery.metisMenu.js"></script>
<script
	src="${spath}/content/js/acms/slimscroll/jquery.slimscroll.min.js"></script>

<!-- Custom and plugin javascript -->
<script src="${spath}/content/js/acms/inspinia.js"></script>
<script src="${spath}/content/js/acms/pace/pace.min.js"></script>
<!-- Steps -->
<script src="${spath}/content/js/acms/staps/jquery.steps.min.js"></script>

<!-- Jquery Validate -->
<script src="${spath}/content/js/acms/validate/jquery.validate.min.js"></script>

<!-- Contabs -->
<script src="${spath}/content/js/acms/contabs.min.js"></script>

<!-- jQuery UI -->
<script src="${spath}/content/js/acms/jquery-ui/jquery-ui.min.js"></script>

<!-- Sparkline -->
<script src="${spath}/content/js/acms/sparkline/jquery.sparkline.min.js"></script>

<!-- ChartJS-->
<script src="${spath}/content/js/acms/chartJs/Chart.min.js"></script>

<!-- Toastr -->
<script src="${spath}/content/js/acms/toastr/toastr.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="${spath}/content/css/plugins/dataTables/jquery.dataTables.css">


<!-- DataTables -->
<script type="text/javascript" charset="utf8"
	src="${spath}/content/js/acms/dataTables/jquery.dataTables.js"></script>

<script type="text/javascript"
	src="${spath}/content/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${spath}/content/js/sessionstatus.js"></script>
<script type="text/javascript">
function checkOldpwd() {
	var flag = false;
	var old = $("#oldpwd").val();
	if (old != null && old != "") {
		$.ajax({
			type : "POST",
			url : "${spath}/user/checkOldpwd",
			data : {
				username : $("#username").val(),
				password : $.md5(old)
			},
			async : false,
			success : function(msg) {
				if (msg == 1) {
					$("#oldpwdErr").html("");
					flag = true;
				} else {
					$("#oldpwdErr").html("密码错误");
				}
			}
		});
	} else {
		$("#oldpwdErr").html("请填写旧密码")
	}
	return flag;
}

function checkNewpwd() {
	var new1 = $("#newpwd1").val();
	var new2 = $("#newpwd2").val();
	if (new1 == null || new1 == "") {
		$("#newpwdErr1").html("请填写新密码")
		return false;
	} else {
		$("#newpwdErr1").html("")
	}
	if (new2 == null || new2 == "") {
		$("#newpwdErr2").html("请确认新密码")
		return false;
	} else {
		$("#newpwdErr2").html("")
	}
	if (new1 == new2) {
		$("#newpwdErr2").html("")
		return true;
	} else {
		$("#newpwdErr2").html("密码不一致")
		return false;
	}
}

function checkSubmit() {
	if (!checkOldpwd()) {
		return false;
	} else if (!checkNewpwd()) {
		return false;
	} else {
		$.ajax({
			type : "POST",
			url : "${spath}/user/submitNewpwd",
			data : {
				username : $("#username").val(),
				password : $.md5($("#newpwd2").val())
			},
			async : false,
			success : function(msg) {
				if (msg.code == 0) {
					alert("密码已修改");
				} else {
					alert("密码修改出错")
				}
			}
		});
	} 
}
</script>

</head>
<body class="gray-bg">
	<div class="row wrapper border-bottom white-bg page-heading">
		<div class="col-lg-10">
			<br>
			<ol class="breadcrumb">
				<li><a>主页</a></li>
				<li class="active"><strong>修改密码</strong></li>
			</ol>
		</div>
		<div class="col-lg-2"></div>
	</div>
	<div class="wrapper wrapper-content animated fadeInRight">
		<form id="form" enctype="multipart/form-data" class="form-horizontal">
			<input type="hidden" id="userid" name="userid" />

			<div class="form-group">
				<label class="col-sm-1 control-label">账号</label>
				<div class="col-sm-4">
					<input type="text" class="form-control" id="username"
						name="username" value="${user.username }" disabled="disabled" /> <span
						class="help-block m-b-none" style="color: red;" id="usernameErr"></span>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-1 control-label">旧密码</label>
				<div class="col-sm-4">
					<input type="password" class="form-control" id="oldpwd"
						name="oldpwd" /> <span class="help-block m-b-none"
						style="color: red;" id="oldpwdErr"></span>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-1 control-label">新密码</label>
				<div class="col-sm-4">
					<input type="password" class="form-control" id="newpwd1"
						name="newpwd1" /> <span class="help-block m-b-none"
						style="color: red;" id="newpwdErr1"></span>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-1 control-label">确认密码</label>
				<div class="col-sm-4">
					<input type="password" class="form-control" id="newpwd2"
						name="newpwd2" /> <span class="help-block m-b-none"
						style="color: red;" id="newpwdErr2"></span>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-4 col-sm-offset-4">
					<button class="btn btn-primary" type="button" onclick="checkSubmit();">确认修改</button>
				</div>
			</div>

		</form>
	</div>
</body>
</html>