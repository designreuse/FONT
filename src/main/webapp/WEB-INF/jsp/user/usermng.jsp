<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge;charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>菜单管理</title>

<link href="${spath }/content/css/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${spath}/content/css/font-awesome/css/font-awesome.css"
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
	
<!-- sweetalert -->
<link href="${spath }/content/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<script type="text/javascript"
	src="${spath}/content/js/acms/sweetalert/sweetalert.min.js"></script>

<style type="text/css">
.inputtext {
	height: 34px;
	width: 150px;
	border: 1px solid RGB(229, 230, 231);
}

.bd {
	border: 1px solid RGB(26, 179, 148);
	height: 34px;
	width: 150px;
}
</style>

<script type="text/javascript" charset="utf-8">
	var oTable;

	$(document).ready(function() {
		initDataTable();
		initModal();
		getRoles();
		select();
	});
	
	function getRoles() {
		$.ajax({
			url : "${spath}/role/getRoles",
			type : "post",
			success : function(result) {
				if (result.code == 0) {
					var clst = result.obj;
					$.each(clst, function(idx, obj) {
						$("select[name='roleid']").append(
								"<option value='"+obj.roleid+"'>"
										+ obj.rolename
										+ "</option>");
					});
				} else {
					swal("获取角色失败", "", "error");
				}
			},
			error : function(error) {
				console.log(error);
			}
		});
	}
	
	// 选择渠道后的触发事件
	function select() {
		$("#searchRole").bind("change", function() {
			oTable.ajax.reload();
		});
	}

	function searchFun() {
		oTable.ajax.reload();
	}

	// 总的处理函数（获取记录信息、分页信息）
	function retrieveData(sSource, aoData, fnCallback) {
		$.ajax({
			url : sSource, // 这个就是请求地址对应sAjaxSource
			data : {
				"aoData" : JSON.stringify(aoData),
				"roleid" : $("#searchRole").val(),
				"name" : $("#searchName").val()
			}, // 这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
			type : 'post',
			dataType : 'json',
			//async : false,
			success : function(result) {
				fnCallback(result); // 把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
			},
			error : function(msg) {
			}
		});
	}

	function initDataTable() {
		oTable = $('#example')
				.DataTable(
						{
							"processing" : true,
							"serverSide" : true,
							"searching" : false,
							"order" : [ [ 0, "desc" ] ],
							"paging" : true,
							"sAjaxSource" : "${spath}/user/query", // 通过ajax实现分页的url路径
							"fnServerData" : retrieveData, // 获取数据的处理函数

							"aoColumnDefs" : [ {
								"orderable" : false,
								"targets" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9 ]
							} ],

							"aoColumns" : [
									{
										'title' : "编号",
										"mDataProp" : "userid"
									},
									{
										'title' : "账号",
										"mDataProp" : "username"
									},
									{
										'title' : "昵称",
										"mDataProp" : "nikename"
									},
									{
										'title' : "用户类型",
										"mDataProp" : "usertype"
									},
									{
										'title' : "角色",
										"mDataProp" : "rolename"
									},
									{
										'title' : "上次登录时间",
										"mDataProp" : "formatetime"
									},
									{
										'title' : "上次登录IP",
										"mDataProp" : "lastloginip"
									},
									{
										'title' : "登录次数",
										"mDataProp" : "logintimes"
									},
									{
										'title' : "状态",
										"mDataProp" : "state",
										"render" : function(data, type, full,
												meta) {
											if (data == 1)
												return '正常';
											else if (data == 0)
												return '关闭';
											else
												return null;
										}
									},
									{
										'title' : "操作",
										"mDataProp" : null,
										"fnCreatedCell" : function(nTd, sData,
												oData, iRow, iCol) {
											$(nTd)
													.html(
															"<a href='javascript:void(0);' "
																	+ "onclick='editFun("
																	+ oData.userid
																	+ ","
																	+ "\""
																	+ oData.username
																	+ "\","
																	+ "\""
																	+ oData.password
																	+ "\","
																	+ "\""
																	+ oData.nikename
																	+ "\","
																	+ oData.roleid
																	+ ","
																	+ oData.state
																	+ ")'>编辑</a>&nbsp;&nbsp;")
													.append(
															"<a href='javascript:void(0);' onclick='deleteFun("
																	+ oData.userid
																	+ ")'>删除</a>");
										}
									}, ],
							language : {
								"processing" : "处理中...",
								"lengthMenu" : "显示 _MENU_ 项结果",
								"zeroRecords" : "没有匹配结果",
								"info" : "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
								"infoEmpty" : "显示第 0 至 0 项结果，共 0 项",
								"infoFiltered" : "(由 _MAX_ 项结果过滤)",
								"infoPostFix" : "",
								"search" : "搜索:",
								"url" : "",
								"emptyTable" : "表中数据为空",
								"loadingRecords" : "载入中...",
								"infoThousands" : ",",
								"paginate" : {
									"first" : "首页",
									"previous" : "上页",
									"next" : "下页",
									"last" : "末页"
								},
								"aria" : {
									"sortAscending" : ": 以升序排列此列",
									"sortDescending" : ": 以降序排列此列"
								}
							}
						});

	}

	// 单选框重置
	function initModal() {
		$("#myModal").on(
				"hidden.bs.modal",
				function() {
					$(this).removeData("bs.modal");
					$("input[type='radio'][name='state']").attr("checked",
							false);
				});

	}

	// 重置表单
	function resetForm() {
		$('form').each(function(index) {
			$('form')[index].reset();
		});
	}

	function addFun() {
		resetForm();
		$("#userid").val("");
		$("#username").val("");
		$("#username").removeAttr("disabled");
		$("#password").val("");
		$("#password").removeAttr("disabled");
		$("#nikename").val("");
		$("input[type='radio'][name='state'][value='1']").attr("checked",
		"checked");
		$("#usernameErr").html("");
		$("#passwordErr").html("");
		$("#myModal").modal("show");
	}

	function editFun(userid, username, password, nikename, roleid, state) {
		resetForm();
		$("#userid").val(userid);
		$("#username").val(username);
		$("#username").attr("disabled", "disabled");
		$("#password").val("********");
		$("#password").attr("disabled", "disabled");
		$("#nikename").val(nikename);
		$("#roleid").val(roleid);
		$("input[type='radio'][name='state'][value='" + state + "']").attr(
				"checked", "checked");
		$("#usernameErr").html("");
		$("#passwordErr").html("");
		$("#myModal").modal("show");
	}

	function saveFun() {
		if ($("#userid").val() == '') {
			addFunAjax();
		} else {
			editFunAjax();
		}

	}

	function deleteFun(userid) {
	    swal({
	        title: "确认删除？",
	        //text: "本操作无法恢复",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确定",
	        cancelButtonText: "取消",
	        //closeOnConfirm: false
	    }, function () {
			$.ajax({
				url : "${spath}/user/delete",
				data : {
					"userid" : userid
				},
				type : "post",
				success : function(result) {
					if (result.code == 0) {
						swal("删除成功", "", "success");
						oTable.ajax.reload();
					} else {
						swal("删除失败", "删除用户失败，请重试", "error");
					}
				},
				error : function(error) {
					swal("删除失败", "服务器未响应，请重试", "error");
				}
			});
	    });
	}

	// 验证名称唯一
	function checkUsername() {
		var name = $("#username").val();
		var flag = false;
		if (name != null && name != "") {
			$.ajax({
				type : "POST",
				url : "${spath}/user/check",
				data : {
					"username" : name
				},
				async : false,
				success : function(msg) {
					if (msg == 0) {
						$("#usernameErr").html("");
						flag = true;
					} else {
						$("#usernameErr").html("该账号已存在");
					}
				}
			});
		} else {
			$("#usernameErr").html("账号不能为空");
		}
		return flag;
	}

	function addFunAjax() {
		if (!checkUsername()) {
			return false;
		}else if ($("#password").val() == null || $("#password").val() == "") {
			$("#passwordErr").html("密码不能为空");
			return false;
		}else {
			$("#form").ajaxSubmit({
				type : "post",
				url : "${spath}/user/add",
				dataType : "json",
				data : {
					md5password : $.md5($("#password").val())
				},
				success : function(result) {
					if (result.code == 0) {
						$("#myModal").modal("hide");
						resetForm();
						swal("添加成功", "", "success");
						oTable.ajax.reload();
					} else {
						swal("添加失败", "添加用户失败，请重试", "");
					}
				},
				error : function() {
					swal("添加失败", "服务器未响应，请重试", "");
				}
			});
		}
	}

	function editFunAjax() {
		$("#form").ajaxSubmit({
			type : "post",
			url : "${spath}/user/update",
			dataType : "json",
			success : function(result) {
				if (result.code == 0) {
					$("#myModal").modal("hide");
					resetForm();
					swal("修改成功", "", "success");
					oTable.ajax.reload();
				} else {
					swal("修改失败", "添加用户失败，请重试", "");
				}
			},
			error : function() {
				swal("修改失败", "服务器未响应，请重试", "");
			}
		});
	}
	
</script>
</head>
<body class="gray-bg">
	<div class="row wrapper border-bottom white-bg page-heading">
		<div class="col-lg-10">
			<br>
			<ol class="breadcrumb">
				<li><a>主页</a></li>
				<li><a>系统设置</a></li>
				<li class="active"><strong>用户管理</strong></li>
			</ol>
		</div>
		<div class="col-lg-2"></div>
	</div>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<table align="center" border="0" cellpadding="0" cellspacing="0"
							width="100%">
							<tbody>
								<tr>
									<td><label class="inline"><a onclick="addFun();"
											href="javascript:void(0);" class="btn btn-primary "
											data-toggle="modal">添加用户</a></label></td>
									<td align="right">角色：<label class="inline"> <select
											class="form-control m-b" name="roleid" id="searchRole"><option
													value="">全部</option></select>
									</label> &nbsp;&nbsp;账号或昵称：<label class="inline"> <input
											type="text" class="inputtext" id="searchName"
											name="searchName" onfocus="this.className='bd'"
											onblur="this.className='inputtext'" />
									</label><label class="inline">&nbsp;&nbsp;<a
											onclick="searchFun();" href="javascript:void(0);"
											class="btn btn-primary " data-toggle="modal">搜索</a></label>
									</td>
								</tr>
							</tbody>
						</table>

						<table id="example" class="cell-border" cellspacing="0"
							width="100%">
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">用户信息</h4>
				</div>
				<div class="modal-body">
					<form id="form" enctype="multipart/form-data"
						class="form-horizontal">
						<input type="hidden" id="userid" name="userid" />

						<div class="form-group">
							<label class="col-sm-2 control-label">账号</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="username"
									name="username" /> <span class="help-block m-b-none"
									style="color: red;" id="usernameErr"></span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="password"
									name="password" /> <span class="help-block m-b-none"
									style="color: red;" id="passwordErr"></span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">昵称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="nikename"
									name="nikename" /> <span class="help-block m-b-none"
									style="color: red;" id="nikenameErr"></span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">角色</label>
							<div class="col-sm-10">
								<select class="form-control m-b" name="roleid" id="roleid"></select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">是否启用</label>
							<div class="col-sm-10">
								<label class="radio-inline"> <input value="1"
									name="state" type="radio" checked="checked"> 是
								</label> <label class="radio-inline"> <input value="0"
									name="state" type="radio"> 否
								</label>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="btnSave"
						onclick="saveFun();">保存</button>
				</div>
			</div>
		</div>
	</div>

</body>
</html>