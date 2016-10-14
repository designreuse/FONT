package com.acms.util;

import java.util.UUID;

public class StringUtil {
	/**
	 * 获取32位String类型的唯一id
	 * @return
	 */
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().trim().replace("-", "");
	}
}
