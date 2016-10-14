package com.acms.util;

public class SelectDataRangeValueUtil {
	public static String selectDataRangeValue(int markid) {
		//1为装机量查询 数据权限  2为达到查询数据权限
		String value = "";
		int mid=markid;
		switch (mid) {
		case  1:
			value="0fcab8fa2d7f4b59b1ca5f3d691b579a";
			break;
		case  2:
			value="4c287fb139f0401ebe88fb0be627ea92";
			break;
		default:
			break;
		}
		return value;
	}

}
