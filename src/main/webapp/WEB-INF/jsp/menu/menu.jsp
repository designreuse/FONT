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
<link href="${spath }/content/css/plugins/sweetalert/sweetalert.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${spath}/content/js/acms/sweetalert/sweetalert.min.js"></script>
	<script type="text/javascript" src="${spath}/content/js/sessionstatus.js"></script>
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
		initBreadcrumb();
		if('${parentid}' == 0){
			$("#back").css('display','none'); 
		}
	});
	
	function initBreadcrumb(){
		var breadcrumb="${breadcrumb}";
		var arr = breadcrumb.split(";");
		var str = "";
		$.each(arr, function(idx, obj) {
			if(idx != arr.length-1){
				str+='<li>'+obj+'</li>';
			}else{
				str+='<li class="active"><strong>'+obj+'</strong></li>';
			}
			
			//alert(arr.length);
		});
		$("#breadcrumb").html(str);
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
				"parentid" : "${parentid}",
				"menuname" : $("#menuname1").val()
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
							"order" : [ [ 0, "asc" ] ],
							"paging" : true,
							"sAjaxSource" : "${spath}/menu/query", // 通过ajax实现分页的url路径
							"fnServerData" : retrieveData, // 获取数据的处理函数

							"aoColumnDefs" : [ {
								"orderable" : false,
								"targets" : [ 1, 2, 3, 4 ]
							} ],

							"aoColumns" : [
									{
										'title' : "排序",
										"mDataProp" : "corder"
									},
									{
										'title' : "菜单名称",
										"mDataProp" : "menuname"
									},
									{
										'title' : "URI",
										"mDataProp" : "uri"
									},
									{
										'title' : "ICON",
										"mDataProp" : "icon"
									},
									{
										'title' : "操作",
										"mDataProp" : "childCount",
										"fnCreatedCell" : function(nTd, sData,
												oData, iRow, iCol) {
											$(nTd)
													.html(
															"<a href='${spath}/menu/index?menuid="
																	+ oData.menuid
																	+ "&menulevel="
																	+ "${menulevel}"
																	+ "&menuname="
																	+ encodeURIComponent(encodeURIComponent(oData.menuname))
																	+ "&breadcrumb="
																	+ encodeURIComponent(encodeURIComponent('${breadcrumb}'))
																	+ "'>查看子菜单("
																	+ sData
																	+ ")</a>&nbsp;&nbsp;")
													.append(
															"<a href='javascript:void(0);' "
																	+ "onclick='editFun("
																	+ oData.menuid
																	+ ","
																	+ "\""
																	+ oData.menuname
																	+ "\",\""
																	+ oData.uri
																	+"\","
																	+ oData.corder
																	+ ",\""
																	+ oData.icon
																	+ "\""
																	+ ")'>编辑</a>&nbsp;&nbsp;")
													.append(
															"<a href='javascript:void(0);' onclick='deleteFun("
																	+ oData.menuid
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
					$("input[type='radio'][name='isone']").attr("checked",
							false);
					$("input[type='radio'][name='autoinstall']").attr(
							"checked", false);
					$("input[type='radio'][name='recommend']").attr("checked",
							false);
					$("input[type='radio'][name='display']").attr("checked",
							false);
				});

	}

	// 重置表单
	function resetForm() {
		$('form').each(function(index) {
			$('form')[index].reset();
		});
	}

	function getAllChannel() {
		$.ajax({
			url : "${spath}/channel/getAll",
			type : "post",
			success : function(result) {
				if (result.code == 0) {
					var clst = result.obj;
					$.each(clst, function(idx, obj) {
						$("select[name='channelid']").append(
								"<option value='"+obj.channelid+"'>"
										+ obj.channelid + " " + obj.channelname
										+ "</option>");
					});
				} else {
					alert("获取渠道信息失败");
				}
			},
			error : function(error) {
				console.log(error);
			}
		});
	}
	var maxseq;
	function getMaxSequence() {
		$.ajax({
			url : "${spath}/menu/getMaxSeq",
			type : "post",
			async : false,
			data : {
				"parentid" : "${parentid}"
			},
			success : function(result) {
				if (result.code == 0) {
					if (result.obj != null) {
						maxseq = result.obj;
					} else {
						maxseq = 1;
					}
				} else {
					swal("获取排序最大值出错", "", "error");
				}
			},
			error : function(error) {
				console.log(error);
			}
		});
	}

	function addFun() {
		getMaxSequence();
		resetForm();
		$("#menuid").val("");
		$("#menuname").val("");
		$("#uri").val("");
		$("#icon").val("");
		$("#corder").attr("max", maxseq + 1);
		$("#corder").attr("value", maxseq + 1);
		$("#errorMsg").html("");
		$("#errorMsg1").html("");
		$("#myModal").modal("show");
	}

	function editFun(menuid, menuname, uri, corder, icon) {
		getMaxSequence();
		resetForm();
		$("#menuid").val(menuid);
		$("#menuname").val(menuname);
		$("#uri").val(uri);
		$("#icon").val(icon);
		$("#corder").val(corder);
		$("#corder").attr("max", maxseq);
		$("#errorMsg").html("");
		$("#errorMsg1").html("");
		$("#myModal").modal("show");
	}

	function saveFun() {
		if ($("#menuid").val() == '') {
			addFunAjax();
		} else {
			editFunAjax();
		}

	}

	function deleteFun(menuid) {
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
				url : "${spath}/menu/delete",
				data : {
					"menuid" : menuid,
					"parentid" : "${parentid}"
				},
				type : "post",
				success : function(result) {
					if (result.code == 0) {
						swal("删除成功", "", "success");
						oTable.ajax.reload();
					} else {
						swal("删除失败", "子菜单删除失败，请重试", "error");
					}
				},
				error : function(error) {
					swal("删除失败", "服务器未正常响应，请重试", "error");
				}
			});
	    	
	    });
	}

	// 验证名称唯一
	function checkFun() {
		var name = $("#menuname").val();
		var flag = false;
		if (name != null && name != "") {
			$.ajax({
				type : "POST",
				url : "${spath}/menu/check",
				data : {
					"parentid" : "${parentid}",
					"menuname" : name
				},
				async : false,
				success : function(msg) {
					if (msg == 0) {
						$("#errorMsg").html("");
						flag = true;
					} else {
						$("#errorMsg").html("该菜单已存在！");
					}
				}
			});
		} else {
			$("#errorMsg").html("菜单名称不能为空！");
		}
		return flag;
	}

	function addFunAjax() {
		if (!checkFun()) {
			return false;
		} if (!numberValidate()) {
			return false;
		} else {
			$("#form").ajaxSubmit({
				type : "post",
				url : "${spath}/menu/add",
				data : {
					"parentid" : "${parentid}",
					"menulevel" : "${menulevel}"
				},
				dataType : "json",
				success : function(result) {
					if (result.code == 0) {
						$("#myModal").modal("hide");
						swal("添加成功", "", "success");
						resetForm();
						oTable.ajax.reload();
					} else {
						swal("添加失败", "添加子菜单失败", "error");
					}
				},
				error : function() {
					swal("添加失败", "服务器未响应", "error");
				}
			});
		}
	}

	function editFunAjax() {
		if (!numberValidate()) {
			return false;
		} else {
			$("#form").ajaxSubmit({
				type : "post",
				url : "${spath}/menu/update",
				data : {
					"parentid" : "${parentid}"
				},
				dataType : "json",
				success : function(result) {
					if (result.code == 0) {
						$("#myModal").modal("hide");
						resetForm();
						swal("修改成功", "", "success");
						oTable.ajax.reload();
					} else {
						swal("修改失败", "修改父菜单失败，请重试", "error");
					}
				},
				error : function() {
					swal("修改失败", "服务器未响应，请重试", "error");
				}
			});
		}
	}
	
	// 回退操作
	function backFun(id) {
		//window.history.back(-1);
		window.location.href = "${spath}/menu/back?id="+id+"&menulevel="+${menulevel}+"&breadcrumb="+encodeURIComponent(encodeURIComponent('${breadcrumb}'));
	}
	
	// 数字正则验证
	function numberValidate(){
		var flag = false;
		var value = $("#corder").val() * 1;
		var min = $("#corder").attr("min") * 1;
		var max = $("#corder").attr("max") * 1;
		var reg = /^[0-9]+$/;  
		if(!reg.test(value)){
			$("#errorMsg1").html("请输入数字！");
		}else{
			if (value < min || value > max) {
				$("#errorMsg1").html("请输入" + min + "-" + max + "范围内的整数！");
			} else {
				$("#errorMsg1").html("");
				flag = true;
			}
		}
		return flag;
	} 
</script>
</head>
<body class="gray-bg">
	<div class="row wrapper border-bottom white-bg page-heading">
		<div class="col-lg-10">
			<br>
			<ol id="breadcrumb" class="breadcrumb">
				<%-- 				<li><a>主页</a></li>
				<li><a>系统设置</a></li>
				<li><a>菜单管理</a></li>
				<li class="active"><strong>【${menuname }】子菜单</strong></li> --%>
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
									<td><a id="back" onclick="backFun(${parentid});"
										href="javascript:void(0);" class="btn btn-primary ">返回</a>&nbsp;&nbsp;&nbsp;&nbsp;<a
										onclick="addFun();" href="javascript:void(0);"
										class="btn btn-primary " data-toggle="modal">添加菜单</a></td>
									<td align="right">菜单名称：<label class="inline"> <input
											type="text" class="inputtext" id="menuname1" name="menuname"
											onfocus="this.className='bd'"
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
					<h4 class="modal-title">菜单信息</h4>
				</div>
				<div class="modal-body">
					<form id="form" enctype="multipart/form-data"
						class="form-horizontal">
						<input type="hidden" id="menuid" name="menuid" />

						<div class="form-group">
							<label class="col-sm-2 control-label">名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="menuname"
									name="menuname" /> <span class="help-block m-b-none"
									style="color: red;" id="errorMsg"></span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">URI</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="uri" name="uri" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">ICON</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="icon" name="icon" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">排序</label>
							<div class="col-sm-10">
								<input type="number" class="form-control" id="corder" min="1"
									name="corder" /><span class="help-block m-b-none"
									style="color: red;" id="errorMsg1"></span>
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