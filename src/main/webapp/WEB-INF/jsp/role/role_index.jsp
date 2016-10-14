<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge;charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>角色管理</title>

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

<!-- jsTree -->
<link href="${spath }/content/css/plugins/jsTree/style.min.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${spath}/content/js/acms/jsTree/jstree.min.js"></script>
	
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
	var r = []; // 权限树中被选中的叶子节点

	$(document).ready(function() {
		initDataTable();
		initModal();
		initMenuTree();
	});
	
	// 多选树
	function initMenuTree() {

		$.getJSON("${spath}/menu/menuTree?parentid=0", function(data) {
			$('#menutree').jstree({
				'plugins':["wholerow","checkbox"],
				//'checkbox': { cascade: "", three_state: false }, //不级联
				'core' : {
					'data' : {
					"text" : "所有菜单",
					"id" : -1,
					"children" : data
				},
	            "themes": {
	                "responsive": false
	            },
				"attr": { "id": "node id", "class":"jstree-checked" }
			}});
		});
		// 展开所有节点
		 $('#menutree').on("ready.jstree", function (e, data) {
		        data.instance.open_all();
		    });
		// 得到选中节点的id
        $('#menutree').on('changed.jstree', function(e, data) {
        	r=data.selected;
/*         	r = []; 
            var i, j;  
            for(i = 0, j = data.selected.length; i < j; i++) {
                r.push(data.instance.get_node(data.selected[i]).id);
              }
            alert('Selected: ' + r);  */
        });
        
	}
	
	// 将已经选择的菜单绑定
    function loadMenuTree(roleid) {
        if (roleid != "") {
            $('#menutree').jstree("uncheck_all");//取消所有选中
            
            //勾选指定内容
            $.getJSON("${spath}/roleMenu/menuTree?roleid="+ roleid, function (json) {
                $.each(json, function (i, item) {
                     $('#menutree').jstree('check_node', item);//将节点选中 
                });
            });
        }
    }

	// 总的处理函数（获取记录信息、分页信息）
	function retrieveData(sSource, aoData, fnCallback) {
		$.ajax({
			url : sSource, // 这个就是请求地址对应sAjaxSource
			data : {
				"aoData" : JSON.stringify(aoData)
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
							"sAjaxSource" : "${spath}/role/query", // 通过ajax实现分页的url路径
							"fnServerData" : retrieveData, // 获取数据的处理函数

							"aoColumnDefs" : [ {
								"orderable" : false,
								"targets" : [ 1, 2, 3, 4, 5 ]
							} ],

							"aoColumns" : [
									{
										'title' : "ID",
										"mDataProp" : "roleid"
									},
									{
										'title' : "角色名称",
										"mDataProp" : "rolename"
									},
									{
										'title' : "创建用户",
										"mDataProp" : "username"
									},
									{
										'title' : "创建时间",
										"mDataProp" : "formattime"
									},
									{
										'title' : "状态",
										"mDataProp" : "state",
										"render" : function(data, type, full,
												meta) {
											if (data == 1)
												return '可用';
											else if (data == 0)
												return '不可用';
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
																	+ "\""
																	+ oData.rolename
																	+ "\","
																	+ oData.state
																	+ ","
																	+ oData.roleid
																	+ ")'>编辑</a>&nbsp;&nbsp;")
													.append(
															"<a href='javascript:void(0);' onclick='deleteFun("
																	+ oData.roleid
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
        $('#menutree').jstree("uncheck_all");//取消所有选中
		$("#roleid").val("");
		$("input[type='radio'][name='state'][value='1']").attr("checked",
				"checked");
		$("#usernameErr").html("");
		$("#myModal").modal("show");
	}

	function editFun(rolename,state,roleid) {
		resetForm();
		loadMenuTree(roleid);
		$("#roleid").val(roleid);
		$("#rolename").val(rolename);
		$("input[type='radio'][name='state'][value='" + state + "']").attr(
				"checked", "checked");
		$("#rolenameErr").html("");
		$("#myModal").modal("show");
	}

	function saveFun() {
		if ($("#roleid").val() == '') {
			addFunAjax();
		} else {
			editFunAjax();
		}

	}

	function deleteFun(roleid) {
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
				url : "${spath}/role/delete",
				data : {
					"roleid" : roleid
				},
				type : "post",
				success : function(result) {
					if (result.code == 0) {
						swal("删除成功", "", "success");
						oTable.ajax.reload();
						//window.location.href = "${spath}/sort/index?parentid="+parentid;
					} else {
						swal("删除失败", "角色删除失败，请重试", "error");
					}
				},
				error : function(error) {
					swal("删除失败", "服务器未正常响应，请重试", "error");
				}
			});
	    });
	}

	// 验证专题名称
	function checkRolename() {
		var name = $("#rolename").val();
		var flag = false;
		if (name != null && name != "") {
			$.ajax({
				type : "POST",
				url : "${spath}/role/check",
				data : {
					"rolename" : name
				},
				async : false,
				success : function(msg) {
					if (msg == 0) {
						$("#rolenameErr").html("");
						flag = true;
					} else {
						$("#rolenameErr").html("该角色已存在");
					}
				}
			});
		} else {
			$("#rolenameErr").html("角色名称不能为空");
		}
		return flag;
	}

	function addFunAjax() {
		if (!checkRolename()) {
			return false;
		} else {
			$("#form").ajaxSubmit({
				type : "post",
				url : "${spath}/role/add",
				data : {
					ids : r
				},
				dataType : "json",
				success : function(result) {
					if (result.code == 0) {
						$("#myModal").modal("hide");
						resetForm();
						swal("添加成功", "", "success");
						oTable.ajax.reload();
					} else {
						swal("添加失败", "添加专题失败", "error");
					}
				},
				error : function() {
					$("#btnSave").text("保存");
					swal("添加失败", "服务器未响应", "error");
				}
			});
		}
	}

	function editFunAjax() {
		if ($("#rolename").val() == null || $("#rolename").val() == "") {
			$("#rolenameErr").html("角色名称不能为空");
			return false;
		} else {
			$("#form").ajaxSubmit({
				type : "post",
				url : "${spath}/role/update",
				data : {
					ids : r
				},
				dataType : "json",
				success : function(result) {
					if (result.code == 0) {
						$("#myModal").modal("hide");
						resetForm();
						swal("修改成功", "", "success");
						oTable.ajax.reload();
					} else {
						swal("修改失败", "修改角色失败，请重试", "error");
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
				<li><a>系统设置</a></li>
				<li class="active"><strong>角色管理</strong></li>
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
											data-toggle="modal">添加角色</a></label></td>
									<!-- 									<td align="right">选择渠道：<label class="inline"> <select
											class="form-control m-b" name="channelid" id="selector"><option
													value="">全部</option></select>
									</label> &nbsp;&nbsp;按名称：<label class="inline"> <input
											type="text" class="inputtext" id="specialtopicname1"
											name="specialtopicname" onfocus="this.className='bd'"
											onblur="this.className='inputtext'" />
									</label><label class="inline">&nbsp;&nbsp;<a
											onclick="searchFun();" href="javascript:void(0);"
											class="btn btn-primary " data-toggle="modal">搜索</a></label>
									</td> -->
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
					<h4 class="modal-title">角色信息</h4>
				</div>
				<div class="modal-body">
					<form id="form" enctype="multipart/form-data"
						class="form-horizontal">
						<input type="hidden" id="roleid" name="roleid" />

						<div class="form-group">
							<label class="col-sm-2 control-label">角色名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="rolename"
									name="rolename" /> <span class="help-block m-b-none"
									style="color: red;" id="rolenameErr"></span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">选择菜单</label>
							<div class="col-sm-10">
								<div id="menutree"></div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">是否启用</label>
							<div class="col-sm-10">
								<label class="radio-inline"> <input value="1"
									name="state" type="radio" checked="" > 是
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