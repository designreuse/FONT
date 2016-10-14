package com.acms.util;

import com.acms.config.WebConfigLoader;

/**用于生成需要存储的路径*/
public class PathUtil {
	
	/**远程路径*/
	public static final String REMOTE="vv.remote";
	/**图片路径*/
	public static final String REMOTE_IMG="vv.remote.imagefolder";
	
	/**静态页面存储路径*/
	public static final String REMOTE_STATIC_WEB_PAGE="vv.remote.staticWebPage";
	
	/**存储模板路径*/
	public static final String LOCAL_TEMPLATE="vv.local.template";
	
	/**图片资源访问路径*/
	public static final String HTTP_IMG="vv.http.img";
	
	/**页面资源访问路径*/
	public static final String HTTP_WEBPAGE="vv.http.webPage";
	
	/**JS资源访问路径*/
	public static final String HTTP_JS="vv.htt.js";
	
	/**根据传入的参数类别获取存储的文件夹路径
	 * @param type 路径类别
	 * @return 类别的目录*/
	public static String getDirPath(String type){
		return WebConfigLoader.getConfig().getConfig(type);
	}
	
	/**根据传入的参数类别获取存储的文件夹路径
	 * @param type 路径类别
	 * @param subDirName 类别目录下子目录名
	 * @param wxAccountIdentifier 微信账号文件名
	 * @return 目录*/
	public static String getSubDirPath(String type,String subDirName,String wxAccountIdentifier){
		return getDirPath(type)+"/"+subDirName+"/"+wxAccountIdentifier;
	}
	
	/**根据传入的参数类别获取存储的文件路径
	 * @param type 路径类别
	 * @param subDirName 类别目录下子目录名
	 * @param wxAccountIdentifier 微信账号文件名
	 * @param fileName 文件名*/
	public static String getFilePath(String type,String subDirName,String wxAccountidentifier,String fileName){
		return getSubDirPath(type,subDirName,wxAccountidentifier)+"/"+fileName;
	}
	
	public static String getImgPath(String imgUrl){
		return getDirPath(HTTP_IMG)+"/"+imgUrl;
	}
}
