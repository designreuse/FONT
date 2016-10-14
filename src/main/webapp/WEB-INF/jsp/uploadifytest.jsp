<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>天湃网络</title>
	<meta charset="UTF-8">
	
	<link rel="stylesheet" href="${spath}/content/js/uploadify/uploadify.css">
	
	<script type="text/javascript" src="${spath}/content/js/jquery/jquery.js"></script>
	<script type="text/javascript" src="${spath}/content/js/uploadify/jquery.uploadify.js"></script>
	
  </head>
  
  <body>
	<div class="container">
		<input type="file" name="file" id="file_upload"   />
		<a href="javascript:$('#file_upload').uploadify('upload','*')">上传文件</a>|
		<a href="javascript:$('#file_upload').uploadify('stop')">停止文件</a>
	</div>
	<script type="text/javascript">
	
	$(document).ready(function() {
		$(function(){
			$("#file_upload").uploadify({
				'swf' : '${spath}/content/js/uploadify/uploadify.swf',  
                'uploader' : '${path}/upload/uploadimg/aims/rom',
                'buttonText' : '选择图片',  
                'height' : 30,  
                'width' : 120,  
                'fileDataName' : 'file',
                'auto':true,  
                'onUploadSuccess' : function(file, data, response) { 
                	data = eval('(' + data + ')');
                	alert(data.msg);
                	alert(data.obj.versionCode);
                	
                }  
            });  
		});
    });  
  	</script>
 </body>
</html>
