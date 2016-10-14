<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge;charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>轮播字体</title>

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
	var fontTable;
	$(document).ready(function() {
		initDataTable();
		initFontTable();
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
	function searchFontFun() {
		fontTable.ajax.reload();
	}

	// 总的处理函数（获取记录信息、分页信息）
	function retrieveData(sSource, aoData, fnCallback) {
		$.ajax({
			url : sSource, // 这个就是请求地址对应sAjaxSource
			// 这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
			data : {
				"aoData" : JSON.stringify(aoData)
			},
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
							"order" : [ [ 2, "asc" ] ],
							"paging" : true,
							"sAjaxSource" : "${spath}/rotation/query", // 通过ajax实现分页的url路径
							"fnServerData" : retrieveData, // 获取数据的处理函数

							"aoColumnDefs" : [ {
								"orderable" : false,
								"targets" : [ 0, 1, 3 ]
							} ],

							"aoColumns" : [
									{
										'title' : "字体名称",
										"mDataProp" : "fontname"
									},
									{
						        		'title' : "轮播图",
						        		"mDataProp": "fontimg",
						        		"sWidth": "10%",
						        		  "render":	function ( data, type, full, meta ) {
							        	      return data == null? '': '<img style="width:60px;" src="' + data + '"/>';
							            }
						        	},
									{
										'title' : "排序",
										"mDataProp" : "rank"
									},
									{
										'title' : "操作",
										"mDataProp" : null,
										"fnCreatedCell" : function(nTd, sData,
												oData, iRow, iCol) {
											$(nTd)
													.html(
															"<a href='javascript:void(0);' class='edit-btn'>修改排序</a>")
													.append(
															"<a href='javascript:void(0);' onclick='deleteFun("
																	+ oData.rotationid
																	+ ")'>&nbsp;&nbsp;删除</a>");
										}
									} ],
							"language" : {
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

		// 单行编辑
		$("#example tbody").on("click", ".edit-btn", function() {
			var tds = $(this).parents("tr").children();
			$.each(tds, function(i, val) {
				var jqob = $(val);
				if (i < 2 || jqob.has('a').length) {
					return true;
				} // 跳过前三项 a标签

				var put;
				var txt = jqob.text();
				if (i == 2) {
					put = $('<input type="number" name="rank" min="1">');
					put.val(txt);
				}
				jqob.html(put);
			});
			$(this).html("保存");
			$(this).toggleClass("edit-btn");
			$(this).toggleClass("save-btn");
		});

		// 保存
		$("#example tbody").on(
				"click",
				".save-btn",
				function() {
					var row = oTable.row($(this).parents("tr"));
					var tds = $(this).parents("tr").children();
					$.each(tds, function(i, val) {
						var jqob = $(val);
						//number
						if (i == 2) {
							var txt = jqob.children("input[type='number']")
									.val();
							jqob.html(txt);
							oTable.cell(jqob).data(txt);//修改DataTables对象的数据
						}
					});

					var data = row.data();
					$.ajax({
						"url" : "${spath}/rotation/update",
						"data" : data,
						"type" : "post",
						"error" : function() {
							swal("服务器未正常响应，请重试", "", "error");
						},
						"success" : function(response) {
							if (response.code == 0) {
								oTable.ajax.reload();
							} else {
								swal("操作失败", "", "error");
							}
						}
					});
					$(this).html("编辑");
					$(this).toggleClass("edit-btn");
					$(this).toggleClass("save-btn");
				});

	}

	function retrieveFontData(sSource, aoData, fnCallback) {
		$.ajax({
			url : sSource,//这个就是请求地址对应sAjaxSource
			data : {
				"aoData" : JSON.stringify(aoData),
				"fontname":$("#searFontname").val()
			},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
			type : 'post',
			dataType : 'json',
			//async : false,
			success : function(result) {
				fnCallback(result);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
			},
			error : function(msg) {
			}
		});
	}

	function initFontTable() {
		fontTable = $('#fontTable').DataTable({
			"processing" : true,
			"serverSide" : true,
			"searching" : false,
			"ordering" : false,
			//"order" : [ [ 4, "desc" ] ],
			"paging" : true,
			"sAjaxSource" : "${spath}/rotation/queryFont", // 通过ajax实现分页的url路径
			"fnServerData" : retrieveFontData, // 获取数据的处理函数

/*  			"aoColumnDefs" : [ {
				"orderable" : false,
				"targets" : [ 0, 1, 2, 3 ]
			} ],  */

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
					}],
			"language" : {
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

	    $('#fontTable tbody').on( 'click', 'tr', function () {
	        if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	        }
	        else {
	        	fontTable.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	        }
	    } );
	    
	}

	function resetForm() {
		$('form').each(function(index) {
			$('form')[index].reset();
		});
	}
	
	function addFun() {
		$("#searFontname").val("");
		fontTable.ajax.reload();
		$("#myModal").modal("show");
	}

	function saveFun() {
/* 		var ids = [];
		// 获取选定行的appid的值(多行)
		$.each(appTable.rows('.selected').data(), function() {
			ids.push(this["appid"]);
		}); */
		// 读取选定行的appid（单行）
		var fontid;
		$.each(fontTable.rows('.selected').data(), function() {
			fontid = this["fontid"];
		}); 
		$.ajax({
			"url" : "${spath}/rotation/add",
			"data" : {
				"fontid" : fontid
			},
			"type" : "post",
			"error" : function() {
				swal("添加失败", "服务器未正常响应，请重试", "error");
			},
			"success" : function(response) {
				if (response.code == 0) {
					$("#myModal").modal("hide");
					swal("添加成功", "", "success");
					oTable.ajax.reload();
				} else {
					swal("添加失败", "添加应用出错，请重试", "error");
				}
			}
		});

	}

	// 回退操作
	function backFun() {
		window.history.back(-1);
	}

	function deleteFun(id) {
		swal({
			title : "确认删除？",
			type : "warning",
			showCancelButton : true,
			confirmButtonColor : "#DD6B55",
			cancelButtonText : "取消",
			confirmButtonText : "确定",
			closeOnConfirm : false,
		}, function() {
			$.ajax({
				url : "${spath}/rotation/delete",
				data : {
					"rotationid" : id
				},
				type : "post",
				success : function(result) {
					if (result.code == 0) {
						swal("删除成功", "", "success");
						oTable.ajax.reload();
					} else {
						swal("删除失败", "删除字体失败，请重试", "error");
					}
				},
				error : function(error) {
					swal("删除失败", "服务器未响应，请重试", "error");
				}
			});
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
				<li><a>字体管理</a></li>
				<li class="active"><strong>轮播字体</strong></li>
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
					<h4 class="modal-title">添加轮播字体</h4>
				</div>
				<div class="modal-body">
					<form id="form" enctype="multipart/form-data"
						class="form-horizontal">

						<div class="form-group">
							<label class="col-sm-2 control-label">字体名称</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="searFontname"
									name="searFontname" />
							</div>
							<div class="col-sm-3">
								<a onclick="searchFontFun();" href="javascript:void(0);"
									class="btn btn-primary " data-toggle="modal">搜索</a>
							</div>
						</div>
					</form>
					<table id="fontTable" class="cell-border" cellspacing="0"
						width="100%">
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="btnSave"
						onclick="saveFun();">添加</button>
				</div>
			</div>
		</div>
	</div>

</body>
</html>