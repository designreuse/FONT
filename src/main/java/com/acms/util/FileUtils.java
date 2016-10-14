package com.acms.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileUtils {
	private static final int BUFFER = 1024;
	
	/**
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File file) throws Exception {// 取得文件大小
		long s = 0;
		if (file.exists()) {
			BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
			s = fis.available();
		} else {
			file.createNewFile();
			System.out.println("文件不存在");
		}
		return s;
	}

	public static long getDirSize(File f) throws Exception// 取得文件夹大小
	{
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getDirSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}
	
	public static long getDirMaxChildSize(File f) throws Exception
	{
		long size = 0;
		File flist[] = f.listFiles();
		if (ValidateUtils.isEmpty(flist)) {
			return 0;
		}
		List<Long> fileSizeList = new ArrayList<Long>();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getDirSize(flist[i]);
			}
			fileSizeList.add(size);
			size = 0;
		}
		Collections.sort(fileSizeList);
//		for (Long long1 : fileSizeList) {
//			System.out.println(long1);
//		}
		return fileSizeList.get(fileSizeList.size()-1);
	}
	
	public static String formetFileSize(long fileS) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
	
    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param sPath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String sPath) {
        Boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }
    
    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    
    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
	
	/**
	 * 解压缩ZIP文件
	 * @param fileName 要解压的全路径文件名
	 * @param filePath	解压后存放文件的路径
	 * @param dir 指定解压目录
	 * @throws Exception
	 */
	public static void unZip(String fileName, String filePath,String dir) throws Exception {
		ZipFile zipFile = new ZipFile(fileName);
		Enumeration emu = zipFile.entries();

		while (emu.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) emu.nextElement();
			if (!entry.getName().startsWith(dir)) {
				continue;
			}
			if (entry.isDirectory()) {
				new File(filePath + entry.getName()).mkdirs();
				continue;
			}
			BufferedInputStream bis = new BufferedInputStream(
					zipFile.getInputStream(entry));

			File file = new File(filePath + entry.getName());
			File parent = file.getParentFile();
			if (parent != null && (!parent.exists())) {
				parent.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);

			byte[] buf = new byte[BUFFER];
			int len = 0;
			while ((len = bis.read(buf, 0, BUFFER)) != -1) {
				fos.write(buf, 0, len);
			}
			bos.flush();
			bos.close();
			bis.close();
		}
		zipFile.close();
	}
	
	/**
	 * 获取压缩包中指定后缀文件的CRC
	 * @param fileName
	 * @param fileExt
	 * @return
	 * @throws Exception
	 */
	public static long getZipEntryCrc(String fileName,String fileExt) throws Exception {
		ZipFile zipFile = new ZipFile(fileName);
		Enumeration emu = zipFile.entries();
		while (emu.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) emu.nextElement();
			if (entry.getName().endsWith(fileExt)) {
				return entry.getCrc();
			}
		}
		zipFile.close();
		return 0;
	}
	
	public static void toFile(String data, String filepath) {
		try {

			File file = new File(filepath);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// true = append file
			//FileWriter fileWritter = new FileWriter(file);
			
			BufferedWriter bufferWritter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
			bufferWritter.write(data);
			bufferWritter.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 /** 压缩单个文件*/
    public static void ZipFile(String filepath ,String zippath) {
    	try {
            File file = new File(filepath);
            File zipFile = new File(zippath);
            InputStream input = new FileInputStream(file);
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            zipOut.putNextEntry(new ZipEntry(file.getName()));
            int temp = 0;
            while((temp = input.read()) != -1){
                zipOut.write(temp);
            }
            input.close();
            zipOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
  //保存url文件到本地
    public static boolean saveUrlAs(String url, String fileName) {
		// 此方法只能用于HTTP协议
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			in = new DataInputStream(new URL(url).openStream());
			out = new DataOutputStream(new FileOutputStream(fileName));
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
//		try {
//			//System.out.println(getDirMaxChildSize(new File("E:\\APKTool\\apktool\\News\\lib")));
//			//System.out.println(formetFileSize(getDirMaxChildSize(new File("E:\\APKTool\\apktool\\News\\lib"))));
//			unZip("E:\\APKTool\\apktool\\Test\\News.apk", "E:\\APKTool\\apktool\\Test\\Demo\\","lib");
//			System.out.println(formetFileSize(getDirMaxChildSize(new File("E:\\APKTool\\apktool\\Test\\Demo\\lib"))));
//			deleteFolder("E:\\APKTool\\apktool\\Test\\Demo\\");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		System.out.println(getZipEntryCrc("E:\\DOWNLOAD\\FlashLight_v19.apk","SF"));
		
	}
}
