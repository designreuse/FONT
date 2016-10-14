package com.acms.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class EncoderHandler{
	private static final String ALGORITHM = "MD5";

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	/**必须制定getBytes的字符集，不然不同平台默认的不同可能导致获取方式不同*/
	private static final String CHAR_SET="UTF-8";
	/**
	 * encode string
	 * 
	 * @param algorithm
	 * @param str
	 * @return String
	 */
	public static String encode(String algorithm, String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(str.getBytes(CHAR_SET));
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * encode By MD5
	 * 
	 * @param str
	 * @return String
	 */
	public static String encodeByMD5(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
			messageDigest.update(str.getBytes(CHAR_SET));
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	public static String encodeMD5(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
			messageDigest.update(str.getBytes(CHAR_SET));
			BigInteger bi = new BigInteger(1, messageDigest.digest());
			return bi.toString(16);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Takes the raw bytes from the digest and formats them correct.
	 * 
	 * @param bytes
	 *            the raw bytes from the digest.
	 * @return the formatted bytes.
	 */
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		System.out.println("111111 MD5  :"
				+ EncoderHandler.encodeByMD5("111111"));
		
		System.out.println("111111 MD5 Big  :"
				+ EncoderHandler.encodeMD5("111111"));
		
		System.out.println("111111 MD5  :"
				+ EncoderHandler.encode("MD5", "111111"));
		System.out.println("111111 SHA1 :"
				+ EncoderHandler.encode("SHA1", "111111"));
	}
}
