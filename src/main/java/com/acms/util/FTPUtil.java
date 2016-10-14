package com.acms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.acms.config.VVConfig;
import com.acms.config.WebConfigLoader;

public class FTPUtil {
	
	private static String url = "10.0.0.80";
	private static int port = 21;
	private static String username = "fileweb";
	private static String password = "cv!86hztp";
	
	static {
		VVConfig config = WebConfigLoader.getConfig();
		if (config.getConfig("vv.ftp.url") == null || config.getConfig("vv.ftp.url").trim().isEmpty()) {
			
		}else{
			url = config.getConfig("vv.ftp.url");
		}
		if (config.getConfig("vv.ftp.port") == null || 
				config.getConfig("vv.ftp.port").trim().isEmpty()) {
			
		}else{
			port = Integer.parseInt(config.getConfig("vv.ftp.port"));
		}
		if (config.getConfig("vv.ftp.username") == null || config.getConfig("vv.ftp.username").trim().isEmpty()) {
			
		}else{
			username = config.getConfig("vv.ftp.username");
		}
		if (config.getConfig("vv.ftp.password") == null || config.getConfig("vv.ftp.password").trim().isEmpty()) {
			
		}else{
			password = config.getConfig("vv.ftp.password");
		}
	}

	/**
	* Description: Upload file to FTP Server
	* @param path FTP Server File Save Path
	* @param filename the name of upload file
	* @param input InputStream
	* @return True if success upload, false if not.
	*/ 
	public static boolean uploadFile(String path, String filename, InputStream input) { 
	    boolean success = false; 
	    FTPClient ftp = new FTPClient(); 
	    try { 
	        int reply; 
	        ftp.connect(url, port);//连接FTP服务器 
	        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器 
	        ftp.login(username, password);//登录 
	        //非文本文件需设置文件类型为二进制
	        ftp.setFileType(FTP.BINARY_FILE_TYPE);
	        reply = ftp.getReplyCode(); 
	        if (!FTPReply.isPositiveCompletion(reply)) { 
	            ftp.disconnect();
	            return success;
	        }
	        createDirecroty(path + "/" + filename,ftp);
	        //ftp.changeWorkingDirectory(path);
	        ftp.storeFile(filename, input);        
	         
	        input.close(); 
	        ftp.logout(); 
	        success = true; 
	    } catch (IOException e) { 
	        e.printStackTrace(); 
	    } finally { 
	        if (ftp.isConnected()) { 
	            try { 
	                ftp.disconnect(); 
	            } catch (IOException ioe) { 
	            } 
	        } 
	    } 
	    return success;
	}
	
	/**
	* Description: 向FTP服务器上传文件
	* @param url FTP服务器hostname
	* @param port FTP服务器端口
	* @param username FTP登录账号
	* @param password FTP登录密码
	* @param path FTP服务器保存目录
	* @param filename 上传到FTP服务器上的文件名
	* @param input 输入流
	* @return 成功返回true，否则返回false
	*/ 
	public static boolean uploadFile(String url,int port,
			String username,String password, 
			String path, String filename, InputStream input) { 
	    boolean success = false; 
	    FTPClient ftp = new FTPClient(); 
	    try { 
	        int reply; 
	        ftp.connect(url, port);//连接FTP服务器 
	        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器 
	        ftp.login(username, password);//登录 
	        //非文本文件需设置文件类型为二进制
	        ftp.setFileType(FTP.BINARY_FILE_TYPE);
	        reply = ftp.getReplyCode(); 
	        if (!FTPReply.isPositiveCompletion(reply)) { 
	            ftp.disconnect(); 
	            return success;
	        }
	        
	        createDirecroty(path + "/" + filename,ftp);
	        //ftp.changeWorkingDirectory(path);
	        ftp.storeFile(filename, input);        
	         
	        input.close(); 
	        ftp.logout(); 
	        success = true; 
	    } catch (IOException e) { 
	        e.printStackTrace(); 
	    } finally { 
	        if (ftp.isConnected()) { 
	            try { 
	                ftp.disconnect(); 
	            } catch (IOException ioe) { 
	            } 
	        } 
	    } 
	    return success;
	}
	
	public static void createDirecroty(String remote,FTPClient ftpClient) throws IOException{   
        String directory = remote.substring(0,remote.lastIndexOf("/")+1);   
        if(!directory.equalsIgnoreCase("/")&&!ftpClient.changeWorkingDirectory(new String(directory.getBytes("utf-8"),"utf-8"))){   
            //如果远程目录不存在，则递归创建远程服务器目录   
            int start=0;   
            int end = 0;   
            if(directory.startsWith("/")){   
                start = 1;   
            }else{   
                start = 0;   
            }   
            end = directory.indexOf("/",start);   
            while(true){   
                String subDirectory = new String(remote.substring(start,end).getBytes("utf-8"),"utf-8");   
                if(!ftpClient.changeWorkingDirectory(subDirectory)){   
                    if(ftpClient.makeDirectory(subDirectory)){   
                        System.out.println(ftpClient.changeWorkingDirectory(subDirectory));;   
                    }else {   
                        System.out.println("创建目录失败");   
                    }   
                }   
                start = end + 1;   
                end = directory.indexOf("/",start);   
                //检查所有目录是否创建完毕   
                if(end <= start){   
                    break;   
                }   
            }   
        }
	}
	public static String getUrl() {
		return url;
	}


	public static void setUrl(String url) {
		FTPUtil.url = url;
	}


	public static int getPort() {
		return port;
	}


	public static void setPort(int port) {
		FTPUtil.port = port;
	}


	public static String getUsername() {
		return username;
	}


	public static void setUsername(String username) {
		FTPUtil.username = username;
	}


	public static String getPassword() {
		return password;
	}


	public static void setPassword(String password) {
		FTPUtil.password = password;
	}


	public static void main(String[] args) {
		String path = "/test";
		String filename = "dl.apk";
		InputStream inp;
		try {
			inp = new FileInputStream(new File("E:\\DOWNLOAD\\APK\\dl.apk"));
			boolean result = uploadFile(url, port, username, password, path, filename, inp);
			System.out.println(result);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
