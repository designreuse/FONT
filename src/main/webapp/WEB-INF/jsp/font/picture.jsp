<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

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

<%-- <!-- Sparkline -->
<script src="${spath}/content/js/acms/sparkline/jquery.sparkline.min.js"></script>

<!-- ChartJS-->
<script src="${spath}/content/js/acms/chartJs/Chart.min.js"></script>

<!-- Toastr -->
<script src="${spath}/content/js/acms/toastr/toastr.min.js"></script> --%>

 <link rel="stylesheet" type="text/css"
	href="${spath}/content/css/plugins/dataTables/jquery.dataTables.css">


<!-- DataTables -->
<script type="text/javascript" charset="utf8"
	src="${spath}/content/js/acms/dataTables/jquery.dataTables.js"></script>

<script type="text/javascript"
	src="${spath}/content/js/jquery/jquery.form.js"></script>

<%-- <script type="text/javascript"
	src="${spath}/styles/js/validate/jquery.validate.min.js"></script> --%>
	
	<style>
    html, body {
      height: 100%;
    }
    #actions {
      margin: 2em 0;
    }


    /* Mimic table appearance */
    div.table {
      display: table;
    }
    div.table .file-row {
      display: table-row;
    }
    div.table .file-row > div {
      display: table-cell;
      vertical-align: top;
      border-top: 1px solid #ddd;
      padding: 8px;
    }
    div.table .file-row:nth-child(odd) {
      background: #f9f9f9;
    }



    /* The total progress gets shown by event listeners */
    #total-progress {
      opacity: 0;
      transition: opacity 0.3s linear;
    }

    /* Hide the progress bar when finished */
    #previews .file-row.dz-success .progress {
      opacity: 0;
      transition: opacity 0.3s linear;
    }

    /* Hide the delete button initially */
    #previews .file-row .delete {
      display: none;
    }

    /* Hide the start and cancel buttons and show the delete button */

    #previews .file-row.dz-success .start,
    #previews .file-row.dz-success .cancel {
      display: none;
    }
    #previews .file-row.dz-success .delete {
      display: block;
    }


  </style>

	<script type="text/javascript" src="${spath}/content/js/acms/dropzone/dropzone.js"></script>
	
	<link href="${spath }/content/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<script type="text/javascript"
	src="${spath}/content/js/acms/sweetalert/sweetalert.min.js"></script>
	<script type="text/javascript" src="${spath}/content/js/sessionstatus.js"></script>
<title>分类</title>
<script type="text/javascript" charset="utf-8">
var oTable;
function sweetSuccess(str){
	swal({   title: str,   type: "success"});
}
function sweetError(str){
	swal({   title: str,   type: "error"});
}
	$(document).ready(function() {
		initDataTable();
		initModal();
		initDropzone();
		//$("#btnSave").click(addFunAjax);	
	});
	function initDropzone(){
		var previewNode = document.querySelector("#template");
	    previewNode.id = "";
	    var previewTemplate = previewNode.parentNode.innerHTML;
	    previewNode.parentNode.removeChild(previewNode);
	
	    var myDropzone = new Dropzone("div#dropzone", { // Make the whole body a dropzone
	      url: "${spath}/app/picture_add?appid=${appid}", // Set the url
	      paramName : "imgFile",
	      thumbnailWidth: 60,
	      thumbnailHeight: 60,
	      parallelUploads: 1,
	      previewTemplate: previewTemplate,
	      autoQueue: false, // Make sure the files aren't queued until manually added
	      previewsContainer: "#previews", // Define the container to display the previews
	      clickable: ".fileinput-button", // Define the element that should be used as click trigger to select files.
	      acceptedFiles: ".png,.jpg,.jpeg"
	    });
	
	    myDropzone.on("addedfile", function(file) {
	      // Hookup the start button
	      file.previewElement.querySelector(".start").onclick = function() { myDropzone.enqueueFile(file); };
	    });
	
	    // Update the total progress bar
	    myDropzone.on("totaluploadprogress", function(progress) {
	      document.querySelector("#total-progress .progress-bar").style.width = progress + "%";
	    });
	
	    myDropzone.on("sending", function(file) {
	      // Show the total progress bar when upload starts
	      document.querySelector("#total-progress").style.opacity = "1";
	      // And disable the start button
	      file.previewElement.querySelector(".start").setAttribute("disabled", "disabled");
	    });
	
	    // Hide the total progress bar when nothing's uploading anymore
	    myDropzone.on("queuecomplete", function(progress) {
	      document.querySelector("#total-progress").style.opacity = "0";
	      //刷新datatables
	      oTable.ajax.reload();
	    });
	
	    // Setup the buttons for all transfers
	    // The "add files" button doesn't need to be setup because the config
	    // `clickable` has already been specified.
	    document.querySelector("#actions .start").onclick = function() {
	      myDropzone.enqueueFiles(myDropzone.getFilesWithStatus(Dropzone.ADDED));
	    };
	    document.querySelector("#actions .cancel").onclick = function() {
	      myDropzone.removeAllFiles(true);
	    };
		
	}

	function initDataTable(){
		oTable = $('#example').DataTable({
			"processing" : true,
			"serverSide" : true,
			"searching" : false,
			"ordering" : false,
			"paging":false,
			"ajax" : {
				"url" : "${spath}/app/picture_query",
				"type" : "POST",
				//"dataSrc" : "obj",
				"data": function (d) {//d 是原始的发送给服务器的数据，默认很长。
                    var param = {};//因为服务端排序，可以新建一个参数对象
                    param.appid = ${appid};
                    return param;//自定义需要传递的参数。
                },
			},
			"aoColumns": [
		        {
		        	'title':'ID',
		            "mDataProp": "pictureid"
		        },
		        {
		        	'title' : "所属应用",
		        	"mDataProp": "appname"},
		        {
		        			'title' : "图片",
		        			"mDataProp": "url",
		      	  "render":	function ( data, type, full, meta ) {
		        	      return data == null? '': '<img style="width:50px;height:50px;" src="' + data + '"/>';
		            } },
		        {
		        	'title' : "操作",
		            "mDataProp": "pictureid",
		            "fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
		                $(nTd).html("<a href='javascript:void(0);' onclick='deleteFun(" + sData + ")'>删除</a>");
		            }
		        },
         ],
         language: {  
             "processing": "处理中...",  
             "lengthMenu": "显示 _MENU_ 项结果",  
             "zeroRecords": "没有匹配结果",  
             "info": "",//"显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",  
             "infoEmpty": "",//"显示第 0 至 0 项结果，共 0 项",  
             "infoFiltered": "",//"(由 _MAX_ 项结果过滤)",  
             "infoPostFix": "",  
             "search": "搜索:",  
             "url": "",  
             "emptyTable": "表中数据为空",  
             "loadingRecords": "载入中...",  
             "infoThousands": ",",  
             "paginate": {  
                 "first": "首页",  
                 "previous": "上页",  
                 "next": "下页",  
                 "last": "末页"  
             },  
             "aria": {  
                 "sortAscending": ": 以升序排列此列",  
                 "sortDescending": ": 以降序排列此列"  
             }  
         }  

		});
		
	}
	
 	function backFun(){
 		window.history.back(-1);
	}
	function deleteFun(id,parentid) {
		swal({
			title : "确定要执行此操作吗?",
			type : "warning",
			showCancelButton : true,
			confirmButtonColor : "#DD6B55",
			cancelButtonText : "取消",
			confirmButtonText : "确定",
			//closeOnConfirm : false,
		}, function() {
			$.ajax({
			    url: "${spath}/app/picture_delete",
			    data: {"id": id},
			    type: "post",
			    success: function (result) {
			        if (result.code==0) {
			        	oTable.ajax.reload();
			        	//window.location.href = "${spath}/sort/index?parentid="+parentid;
			        } else {
			            sweetError("删除失败");
			        }
			    }, error: function (error) {
			        console.log(error);
			    }
			});
		});
		
	} 

 	function initModal() {
 		//V2
 	    /* $("#myModal").on("hide", function() {  
 	          $(this).removeData("modal");  
 	     });   */
 		//V3
 	   	$("#myModal").on("hidden.bs.modal", function() {  
 	         $(this).removeData("bs.modal");  
 	        $("input[type='radio'][name='state']").attr("checked",false);
 		});  
 	   /*  $("#myModal").on("show", function() {  
 	    	
	     });   */
	} 
 	function resetFrom() {
		$('form').each(function (index) {
		    $('form')[index].reset();
		});
	}
	 function addFun(){
		resetFrom();
		$("#url").removeAttr("src");
		$("#myModal").modal("show");
	}
	 /*
	function addFunAjax() {

		$("#form").ajaxSubmit({
		     type: "post",
		     url: "${spath}/app/picture_add",
		     dataType: "json",
		     success: function(result){
		    	 if (result.code == 0) {
			            $("#myModal").modal("hide");
			            resetFrom();
			            oTable.ajax.reload();
			        } else{
			            alert("提交失败");
			        }
		     }
		 });
	} */
	
</script>
</head>
<body class="gray-bg">
	<div class="row wrapper border-bottom white-bg page-heading">
		<div class="col-lg-10">
			<h2></h2>
			<ol id="breadcrumb" class="breadcrumb">
				<li>主页</li>
				<li>应用中心</li>
				<li>应用管理</li>
				<li class="active"><strong>图片管理</strong></li>
			</ol>
		</div>
		<div class="col-lg-2"></div>
	</div>
	<div>
		<div class="wrapper wrapper-content animated fadeInRight">
			<div class="row">
				<div class="col-lg-12">
					<div class="ibox float-e-margins">
						<div class="ibox-content">
							<a id="back" onclick="backFun();" href="javascript:void(0);"
								class="btn btn-primary ">返回</a> <a onclick="addFun();"
								href="javascript:void(0);" class="btn btn-primary "
								data-toggle="modal">添加图片</a>

							<table id="example" class="display" cellspacing="0" width="100%">
							</table>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
	 
 	<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<!-- <i class="fa fa-laptop modal-icon"></i>	 -->
					<h4 class="modal-title">图片信息</h4>
				</div>
				<div class="modal-body">
					<%-- <form id = "form" enctype="multipart/form-data" class="form-horizontal">
						<input type="hidden" name="appid" value="${appid}"> 
	
						
						<div class="form-group">
							<label  class="col-sm-2 control-label">上传图标</label>
							<div class="col-sm-10">
								<input type="file" id="firstDemoImgFile" name="imgFile">
							</div>
						</div>

					</form> --%>
						<div id="dropzone"></div>
    <div id="actions" class="row">

      <div class="col-lg-7">
        <!-- The fileinput-button span is used to style the file input field as button -->
        <span class="btn btn-success fileinput-button">
            <i class="glyphicon glyphicon-plus"></i>
            <span>添加图片</span>
        </span>
        <button type="submit" class="btn btn-primary start">
            <i class="glyphicon glyphicon-upload"></i>
            <span>开始上传</span>
        </button>
        <button type="reset" class="btn btn-warning cancel">
            <i class="glyphicon glyphicon-ban-circle"></i>
            <span>取消上传</span>
        </button>
      </div>
      <div class="col-lg-5">
        <!-- The global file processing state -->
        <span class="fileupload-process">
          <div id="total-progress" class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">
            <div class="progress-bar progress-bar-success" style="width:0%;" data-dz-uploadprogress></div>
          </div>
        </span>
      </div>
    </div>
    <div class="table table-striped files" id="previews">

      <div id="template" class="file-row">
        <!-- This is used as the file preview template -->
        <div>
            <span class="preview"><img data-dz-thumbnail /></span>
        </div>
        <div>
            <p class="name" data-dz-name></p>
            <strong class="error text-danger" data-dz-errormessage></strong>
        </div>
        <div>
            <p class="size" data-dz-size></p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">
              <div class="progress-bar progress-bar-success" style="width:0%;" data-dz-uploadprogress></div>
            </div>
        </div>
        <div>
          <button class="btn btn-primary start">
              <i class="glyphicon glyphicon-upload"></i>
              <span>开始</span>
          </button>
          <button data-dz-remove class="btn btn-warning cancel">
              <i class="glyphicon glyphicon-ban-circle"></i>
              <span>取消</span>
          </button>
          <button data-dz-remove class="btn btn-danger delete">
            <i class="glyphicon glyphicon-trash"></i>
            <span>删除</span>
          </button>
        </div>
      </div>

    </div>
					
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
						<!-- <button type="button" class="btn btn-primary" id="btnSave">保存</button> -->
					</div>
				</div>
			</div>
	</div>  
</body>

</html>