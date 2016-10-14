<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge;charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>字体管理</title>

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

<!-- select2 -->
<link rel="stylesheet" type="text/css"
	href="${spath}/content/js/select2n/css/select2.min.css">

<script type="text/javascript"
	src="${spath}/content/js/select2n/js/select2.min.js"></script>

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

span.select2-container {
	z-index: 10050;
}
</style>

<script type="text/javascript" charset="utf-8">
	var oTable;

	$(document).ready(function() {
		initDataTable();
		initModal();
	});
	
	function getLocalTime(nS) {
		var date = new Date(nS);
		Y = date.getFullYear() + '-';
		M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date
				.getMonth() + 1)
				+ '-';
		D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
		h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours())
				+ ':';
		m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date
				.getMinutes())
				+ ':';
		s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date
				.getSeconds();
		return Y + M + D + h + m + s;
		//   return new Date(parseInt(nS) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");      
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
				"fontname" : $("#searFontname").val()
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
							"ordering" : true,
							"order" : [ [ 8, "asc" ] ],
							"paging" : true,
							"sAjaxSource" : "${spath}/font/query", // 通过ajax实现分页的url路径
							"fnServerData" : retrieveData, // 获取数据的处理函数

							"aoColumnDefs" : [ {
								"orderable" : false,
								"targets" : [ 0, 1, 2, 3, 4, 5, 6, 7, 9 ]
							} ],

							"aoColumns" : [
									{
										'title' : "字体名称",
										"mDataProp" : "fontname"
									},
									{
						        		'title' : "图标",
						        		"mDataProp": "fonticon",
						        		"sWidth": "10%",
						        		  "render":	function ( data, type, full, meta ) {
							        	      return data == null? '': '<img style="width:30px;" src="' + data + '"/>';
							            }
						        	},
						        	{
						        		'title' : "示例图",
						        		"mDataProp": "fontimg",
						        		"sWidth": "10%",
						        		  "render":	function ( data, type, full, meta ) {
							        	      return data == null? '': '<img style="width:60px;" src="' + data + '"/>';
							            }
						        	},
									{
										'title' : "描述",
										"mDataProp" : "fontdesc"
									},
									{
										'title' : "标签",
										"mDataProp" : "fonttags"
									},
									{
						        		'title' : "下载地址",
						        		"mDataProp": "downurl",
					        			"render" : function(data, type, full,
												meta) {
											if (data == null || data == '')
												return '';
											return "<a href = '"+data+"'>"
													+ data + "</a>";
										}
						        	},
						        	{
										'title' : "更新时间",
										"mDataProp" : "updatetime",
										"render" : function(data, type, full,
												meta) {
											if (data == null || data == '')
												return '';
											return getLocalTime(data);
										}
									},
									{
										'title' : "上传用户",
										"mDataProp" : "username"
									},
									{
										'title' : "排序",
										"mDataProp" : "wholerank"
									},
									{
										'title' : "操作",
										"mDataProp" : "fontid",
										"fnCreatedCell" : function(nTd, sData,
												oData, iRow, iCol) {
											$(nTd)
													.html(
															"<a href='javascript:void(0);' "
															+ "onclick='editFun(" + iRow+")'>编辑</a>&nbsp;&nbsp;")
													.append(
															"<a href='javascript:void(0);' onclick='deleteFun("
															+ oData.fontid
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
				});
	}

	// 重置表单
	function resetForm() {
		$('#form')[0].reset();	
		//清除隐藏域
		$('form input[type="hidden"]').val("");
		// 清除单选框
		//$("input[type='radio']").prop('checked', false);
		$("#form span").html("");
	}
	
	// 自动填充form表单中的值
	function autoFill(obj, formid) {
		for ( var attr in obj) {
			if (obj[attr] == null || typeof (obj[attr]) == 'function') {
				continue;
			}
			var $input = $("#" + formid + " :input[name='" + attr + "']");
			var type = $input.attr("type");
			if (type == "checkbox" || type == "radio") {
				if (typeof obj[attr] == 'string') {
					var avalues = obj[attr].split(",");
					for (var v = 0; v < avalues.length; v++) {
						$input.each(function(i, n) {
							var value = $(n).val();
							if (value == avalues[v]) {
								$(n).prop("checked", "checked");
							}
						});
					}
				} else {
					avalue = obj[attr];
					$input.each(function(i, n) {
						var value = $(n).val();
						if (value == avalue) {
							$(n).prop("checked", "checked");
						}
					});
				}

			} else {
				$input.val(obj[attr]);
			}
			var $a = $("#" + formid + " a[name='" + attr + "']");//<a href="" name="sturl" target="_blank"></a>
			if ($a != null) {
				$a.attr("href", obj[attr]);
				$a.text(obj[attr]);
			}
		}
	}

	function addFun() {
		resetForm();
		//$("input[type='radio'][name='taskstatus'][value='0']").prop("checked","checked");
		$("#myModal").modal("show");
	}

	function editFun(iRow) {
		var obj = oTable.row(iRow).data();
		resetForm();
		autoFill(obj, "form");
		$("#myModal").modal("show");
	}

	function saveFun() {
		if ($("#fontid").val() == '') {
			addFunAjax();
		} else {
			editFunAjax();
		}
	}
	
	function deleteFun(fontid) {
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
				url : "${spath}/font/delete",
				data : {
					"fontid" : fontid
				},
				type : "post",
				success : function(result) {
					if (result.code == 0) {
						swal("删除成功", "", "success");
						oTable.ajax.reload();
						//window.location.href = "${spath}/sort/index?parentid="+parentid;
					} else {
						swal("删除失败", "字体删除失败，请重试", "error");
					}
				},
				error : function(error) {
					swal("删除失败", "服务器未正常响应，请重试", "error");
				}
			});
	    });
	}
	
	function validateForm() {
/* 		var taskname = $("#taskname").val();
		var tasktype = $("input[name='tasktype']:checked").val()
		var priority = $("input[name='priority']:checked").val()
		if (taskname==null || taskname=="") {
			$("#tasknameErr").html("请填写任务名称");
			return false;
		} else {
			$("#tasknameErr").html("");
		}
		if (tasktype==null || tasktype=="") {
			$("#tasktypeErr").html("请选择任务类型");
			return false;
		} else {
			$("#tasktypeErr").html("");
		}
		if (priority==null || priority=="") {
			$("#priorityErr").html("请选择优先级");
			return false;
		} else {
			$("#priorityErr").html("");
		} */
		var wholerank = $("#wholerank").val();
		if (wholerank !=null && wholerank != "") {
			$("#wholerankErr").html("");
			return true;
		} else {
			$("#wholerankErr").html("请填写排序");
			return false;
		}
	}

	function addFunAjax() {
		if (validateForm()) {
			$("#form").ajaxSubmit({
				type : "post",
				url : "${spath}/font/add",
				dataType : "json",
				success : function(result) {
					if (result.code == 0) {
						$("#myModal").modal("hide");
						swal("添加成功", "", "success");
						oTable.ajax.reload();
					} else {
						swal("添加失败", "添加字体失败", "error");
					}
				},
				error : function() {
					swal("添加失败", "服务器未响应", "error");
				}
			});
		}
	}

	function editFunAjax() {
		if (validateForm()) {
			$("#form").ajaxSubmit({
				type : "post",
				url : "${spath}/font/update",
				dataType : "json",
				success : function(result) {
					if (result.code == 0) {
						$("#myModal").modal("hide");
						swal("修改成功", "", "success");
						oTable.ajax.reload();
					} else {
						swal("修改失败", "修改字体失败，请重试", "error");
					}
				},
				error : function() {
					swal("修改失败", "服务器未响应，请重试", "error");
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
				<li><a>字体管理</a></li>
				<li class="active"><strong>所有字体</strong></li>
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
									<td><a onclick="addFun();" href="javascript:void(0);"
										class="btn btn-primary " data-toggle="modal">添加字体</a></td>


									<td align="right">按名称：<label class="inline"> <input
											type="text" class="inputtext" id="searFontname"
											name="searFontname" onfocus="this.className='bd'"
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
					<h4 class="modal-title">字体信息</h4>
				</div>
				<div class="modal-body">
					<form id="form" enctype="multipart/form-data"
						class="form-horizontal">
						<input type="hidden" id="fontid" name="fontid" />

						<div class="form-group">
							<label class="col-sm-2 control-label">字体名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="fontname"
									name="fontname" /> <span class="help-block m-b-none"
									style="color: red;" id="fontnameErr"></span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">字体描述</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="fontdesc"
									name="fontdesc" /> <span class="help-block m-b-none"
									style="color: red;" id="fontdescErr"></span>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">标签</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="fonttags"
									name="fonttags" /> <span class="help-block m-b-none">注意：多个标签请用空格分隔</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">排序</label>
							<div class="col-sm-10">
								<input type="number" min="1"  class="form-control" id="wholerank"
									name="wholerank" /><span class="help-block m-b-none"
									style="color: red;" id="wholerankErr"></span>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">上传字体文件</label>
							<div class="col-sm-10">
								<label class="inline"><input type="file" id="fontFile"
									name="fontFile"> </label>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">上传图标</label>
							<div class="col-sm-10">
								<label class="inline"><input type="file" id="iconFile"
									name="iconFile"> </label><label class="inline"><img
									style='width: 40px; height: 25px;' id='fonticon' />&nbsp;&nbsp;尺寸：--</label>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">上传示例图</label>
							<div class="col-sm-10">
								<label class="inline"><input type="file" id="imgFile"
									name="imgFile"> </label><label class="inline"><img
									style='width: 40px; height: 25px;' id='fontimg' />&nbsp;&nbsp;尺寸：--</label>
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