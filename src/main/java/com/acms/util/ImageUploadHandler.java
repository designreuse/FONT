package com.acms.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class ImageUploadHandler{
	private Logger logger = Logger.getLogger(this.getClass());
	private String location;
	// data chunk be written per time
    private static final int DATA_CHUNK = 128 * 1024 * 1024; 
 
    // total data size is 2G
    private static final long LEN = 2L * 1024 * 1024 * 1024L; 

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}


	
	public String saveFile(byte[] content, String filename, String path)
			throws IOException {
		logger.debug("Save Content" + filename + " To:" + path);
		File fileDir=new File(path);
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
		FileOutputStream fo=new FileOutputStream(new File(path)+"/"+filename);
		try {
			fo.write(content);
		} catch (Exception e) {
			throw new IOException(e);
		}finally{
			fo.flush();
			fo.close();
		}
		return filename;
	}
	
	public String saveFile(InputStream ips, String filename, String path)
			throws IOException {
		logger.debug("Save Content" + filename + " To:" + path);
		File fileDir=new File(path);
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
		FileOutputStream fo = new FileOutputStream(new File(path)+"/"+filename);
		byte [] bt = new byte[1024];
		try {
			int len;
			while ((len = ips.read(bt)) > 0) {
				fo.write(bt, 0, len);
			}
		} catch (Exception e) {
			throw new IOException(e);
		}finally{
			fo.flush();
			fo.close();
		}
		return filename;
	}

	public void removeFile(String filename, String path) throws IOException {
		logger.info("Delete Content" + filename + " To:" + location);
		File file=new File(path+"/"+filename);
		if(file.exists()){
			file.delete();
		}
	}
	
	/**
	 * 上传文件
	 * @param file	从前台表单中提取的MultipartFile类型文件
	 * @param fileDir	转存的文件目录
	 * @return	文件上传到cdn以后返回的url地址
	 */
	public String uploadFile(MultipartFile file, String fileDir, Map map) {
		String url = ""; // 文件上传以后的名称
		try {

			File filePath = new File(fileDir);
			// 如果文件夹不存在则创建
			if (!filePath .exists()) {
				filePath.mkdirs();
			}
	        // 获取后缀名
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
	        // 获取系统文件名，系统文件名由当前时间的值加上3位随机生成吗构成
	        String fileName = new Long(System.currentTimeMillis()).toString() + new Random().nextInt() % 1000 + suffix;
	        String filepath = "";
	        
			if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
				filepath = fileDir.replace("\\", "\\\\")+"\\"+fileName;
			}else{
				filepath = fileDir+"/"+fileName;
			}
			
			// 首先传到tomcat服务器
			file.transferTo(new File(filepath));
			
			// 上传到cdn
			OSSUtil ossUtil = new OSSUtil();
			ossUtil.putObject((String)map.get("bucket"), map.get("subDir")+fileName, filepath);
			url = (String)map.get("context") + map.get("subDir")+fileName;
			logger.info("上传到阿里云的图片地址是：" + url);
		} catch (Exception e) {
			logger.error("上传文件到cdn失败！", e);
		}
		return url;
	}
	
	public static void main(String[] args) {
		OSSUtil ossUtil = new OSSUtil();
		// 删除
/*		List<String> list = ossUtil.listObjects("acms", "ad_apk/splash/8-18/");
		for (String string : list) {
			ossUtil.deleteObject("acms", string);
		}*/
		
		// 上传
		File file = new File("C:/Users/hzyq/Desktop/开屏图片");
		File[] flist = file.listFiles();
		for (File f : flist) {
			try {
				ossUtil.putObject("acms", "ad_apk/splash/8-18/" + f.getName(), f.getAbsolutePath());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
