package com.acms.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

public class CRC32Util {

	public static String getCRC32(String fileUri) {
		CRC32 crc32 = new CRC32();
		FileInputStream fileinputstream = null;
		CheckedInputStream checkedinputstream = null;
		String crc = null;
		try {
			BufferedInputStream buf = new BufferedInputStream(new FileInputStream(new File(fileUri)));
			checkedinputstream = new CheckedInputStream(buf, crc32);
			while (checkedinputstream.read() != -1) {
			}
			crc = Long.toHexString(crc32.getValue()).toUpperCase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileinputstream != null) {
				try {
					fileinputstream.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			if (checkedinputstream != null) {
				try {
					checkedinputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return crc;
	}

	public static long checksumInputStream(String filepath) throws IOException {
		InputStream inputStreamn = new FileInputStream(filepath);
		CRC32 crc = new CRC32();

		int cnt;

		while ((cnt = inputStreamn.read()) != -1) {
			crc.update(cnt);
		}
		return crc.getValue();
	}

	public static long checksumBufferedInputStream(String filepath) throws IOException {
		InputStream inputStream = new BufferedInputStream(new FileInputStream(filepath));

		CRC32 crc = new CRC32();

		int cnt;

		while ((cnt = inputStream.read()) != -1) {
			crc.update(cnt);
		}
		return crc.getValue();
	}

	public static long checksumRandomAccessFile(String filepath) throws IOException {
		RandomAccessFile randAccfile = new RandomAccessFile(filepath, "r");
		long length = randAccfile.length();
		CRC32 crc = new CRC32();

		for (long i = 0; i < length; i++) {
			randAccfile.seek(i);
			int cnt = randAccfile.readByte();
			crc.update(cnt);
		}
		return crc.getValue();
	}

	/*
	 * public static long checksumMappedFile(String filepath) throws IOException
	 * { FileInputStream inputStream = new FileInputStream(filepath);
	 * FileChannel fileChannel = inputStream.getChannel();
	 * 
	 * int len = (int) fileChannel.size();
	 * 
	 * MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY,
	 * 0, len);
	 * 
	 * CRC32 crc = new CRC32();
	 * 
	 * for (int cnt = 0; cnt < len; cnt++) {
	 * 
	 * int i = buffer.get(cnt); crc.update(i); } return crc.getValue(); }
	 */

	public static String CheckCrc(String crc) {
		String crc32 = "00000000";
		if (crc.length() < 8)
			crc32 = String.format("%1$0" + (8 - crc.length()) + "d", 0) + crc;
		return crc32;
	}
}
