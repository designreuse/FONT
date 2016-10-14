package com.acms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;

public class OSSUtil {

	private String accessKeyId = "j7m2E8aDnlE77Aes";
	private String accessKeySecret = "3bsEgi1sAv6hY1hKVzdN1uKZUG7uXm";

    // 初始化一个OSSClient
	private OSSClient client;
    
	public OSSUtil() {
		super();
		this.client =new OSSClient(accessKeyId, accessKeySecret);
	}
	public OSSUtil(String accessKeyId, String accessKeySecret) {
		super();
		
		this.client =new OSSClient(accessKeyId, accessKeySecret);
	}
	public void createBucket(String bucketName) {
	    // 新建一个Bucket
	    client.createBucket(bucketName);
	}
	public void putObject(String bucketName, String key, String filePath) throws FileNotFoundException {
	    // 获取指定文件的输入流
	    File file = new File(filePath);
	    InputStream content = new FileInputStream(file);

	    // 创建上传Object的Metadata
	    ObjectMetadata meta = new ObjectMetadata();

	    // 必须设置ContentLength
	    meta.setContentLength(file.length());
	    if (filePath.endsWith(".xml")) {
        	meta.setContentType("text/xml");
		}else if (filePath.endsWith(".jpg")) {
			meta.setContentType("image/jpeg");
		}else if (filePath.endsWith(".png")) {
			meta.setContentType("image/png");
		}else if(filePath.endsWith(".htm") || filePath.endsWith(".html")){
			meta.setContentType("text/html");
		}else if(filePath.endsWith(".css")){
			meta.setContentType("text/css");
		}else if(filePath.endsWith(".js")){
			meta.setContentType("application/javascript");
		}
	    //client.deleteObject(bucketName, key);
	    // 上传Object.
	    PutObjectResult result = client.putObject(bucketName, key, content, meta);

	    // 打印ETag
	    System.out.println(result.getETag());
	}
	
	public void deleteObject(String bucketName, String key) {
	    // 删除Object
	    client.deleteObject(bucketName, key);
	}
	
	public void listObjects(String bucketName) {

	    // 获取指定bucket下的所有Object信息
	    ObjectListing listing = client.listObjects(bucketName);

	    // 遍历所有Object
	    for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
	        System.out.println(objectSummary.getKey());
	    }
	}
	
	public List<String> listObjects(String bucketName, String key) {
		
		List<String> keyList = new ArrayList<String>();
	    // 获取指定bucket下的所有Object信息
	    ObjectListing listing = client.listObjects(bucketName, key);
	    // 遍历所有Object
	    for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
	    	System.out.println(objectSummary.getKey());
	    	keyList.add(objectSummary.getKey());
	    }
	    return keyList;
	}
}
