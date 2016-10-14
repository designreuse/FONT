package com.acms.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Filter {

	/**
	 * 计算文件MD5值，修改计算方式为Buffer，Mapped有大小限制。
	 * @param file
	 * @return
	 */
	public static String getMd5ByFile(File file) {
		try {
			return getMd5ByFile(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getMd5ByFile(InputStream in) {
		String value = null;
		try {
			in = new BufferedInputStream(in);
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024];
			
			int numRead = 0;
			while((numRead = in.read(buffer)) > 0){
				md5.update(buffer,0,numRead);
			}
			in.close();
			
			byte b[] = md5.digest();
			int i;
			StringBuilder sb = new StringBuilder("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(i));
			}
			return sb.toString();

//			BigInteger bi = new BigInteger(1, md5.digest());
//			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	/**
	 * 计算文件MD5值，Mapped方式。
	 * @param file
	 * @return
	 */
	public static String getMd5MappedByFile(File file) {
		String value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			MappedByteBuffer byteBuffer = in.getChannel().map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());
			md5.update(byteBuffer);
			
			in.close();
			
			byte b[] = md5.digest();
			int i;
			StringBuilder sb = new StringBuilder("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(i));
			}
			return sb.toString();
//			BigInteger bi = new BigInteger(1, md5.digest());
//			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	public static String getStringMd5(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(str.getBytes("UTF-8"));
			BigInteger bi = new BigInteger(1, messageDigest.digest());
			return bi.toString(16);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getMD5(String sourceStr, int number) {
		String result32 = "";
		String result16 = "";
		if (sourceStr == null) {
			return null;
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sourceStr.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			result32 = buf.toString();
			result16 = buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (number == 32) {
			return result32;
		} else {
			return result16;
		}

	}
	public static void main(String[] args) {
//		String v = MD5Filter.getMd5ByFile(new File(
//				"E:\\DOWNLOAD\\APK\\dl.apk"));
		long startTime = System.currentTimeMillis();
		
		File file = new File("C:\\Users\\hztp\\Desktop\\SM-G7108V_CHC-G7108VZMUANI2_20150530.rar");
		
		//file = new File("E:\\Software\\X16-60997VS2010UltimTrialCHS.iso");
		String v = MD5Filter.getMd5MappedByFile(file);
		
		System.out.println("delete result:" + file.delete());
		
		System.out.println(v);
		System.out.println("Cost Time:" + (System.currentTimeMillis() - startTime));
		
		
	}
	
}
