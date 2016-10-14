package com.acms.constans;

import java.util.regex.Pattern;

/**正则表达式*/
public class VvReg {
	
	/**第二个字母大写正则表达式*/
	public static final String SECONDUPPER="^[a-z]{1}[A-Z]{1}[a-zA-Z\\d]{0,}+$";
	
	/**数字正则表达式*/
	public static final String DIGITS="^\\d+$";
	
	/**字母正则表达式*/
	public static final String LETTERS="^[a-zA-Z]+$";
	
	/**电话号码正则表达式*/
	public static final String TEL="^(?:(?:0\\d{2,3}[- ]?[1-9]\\d{6,7})|(?:[48]00[- ]?[1-9]\\d{6}))$";
	
	/**手机号码正则表达式*/
	public static final String MOBILE="^1[3-9]\\d{9}$";
	
	/**邮箱正则表达式*/
	public static final String EMAIL="^(?:[a-zA-Z0-9]+[_\\-+.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+-?)*[a-zA-Z0-9]+\\.)+([a-zA-Z]{2,})+$";
	
	/**qq正则表达式*/
	public static final String QQ="^[1-9]\\d{4,}$";
	
	/**日期正则表达式*/
	public static final String DATE= "^\\d{4}-\\d{1,2}-\\d{1,2}$";
	
	/**时间正则表达式*/
	public static final String TIME="^([01]\\d|2[0-3])(:[0-5]\\d){1,2}$";
	
	/**省份证正则表达式*/
	public static final String ID_CARD= "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[A-Z])$";
	
	/**网址格式正则表达式*/
	public static final String URL= "^(https?|ftp):\\/\\/[^\\s]+$";
	
	/**邮政编码正则表达式*/
	public static final String POSTCODE="^[1-9]\\d{5}$";
	
	/**中文正则表达式*/
	public static final String CHINESE="^[\u0391-\uFFE5]+$";
	
	/**用户名正则表达式*/
	public static final String USERNAME="^\\w{3,12}$";
	
	/**密码正则表达式*/
	public static final String PASSWORD="^[0-9a-zA-Z_&#$!]{4,16}$";
	
	/**匹配正则表达式
	 * @param msg 需要匹配的字符串
	 * @param reg 正则表达式对应的编码
	 * @return 匹配成功返回空字符串
	 * 匹配失败返回对应的信息*/
	public static String match(String reg,String msg){
		if(reg==null){
			return "正则表达式为null";
		}
		if(msg==null){
			return "匹配的字符断为null";
		}
		if(!Pattern.matches(reg, msg)){
			if(DIGITS.equals(reg)){
				return "请输入数字";
			}else if(LETTERS.equals(reg)){
				return "只能输入字母";
			}else if(TEL.equals(reg)){
				return "电话格式不正确";
			}else if(MOBILE.equals(reg)){
				return "手机号格式不正确";
			}else if(EMAIL.equals(reg)){
				return "邮箱格式不正确";
			}else if(QQ.equals(reg)){
				return "QQ号格式不正确";
			}else if(DATE.equals(reg)){
				return "请输入正确的日期,例:yyyy-mm-dd";
			}else if(TIME.equals(reg)){
				return "请输入正确的时间,例:14:30或14:30:00";
			}else if(ID_CARD.equals(reg)){
				return "请输入正确的身份证号码";
			}else if(URL.equals(reg)){
				return "网址格式不正确";
			}else if(POSTCODE.equals(reg)){
				return "邮政编码格式不正确";
			}else if(CHINESE.equals(reg)){
				return "请输入中文";
			}else if(USERNAME.equals(reg)){
				return "请输入3-12位数字、字母、下划线";
			}else if(PASSWORD.equals(reg)){
				return "密码由4-16位数字、字母组成";
			}else if(SECONDUPPER.equals(reg)){
				return "首字母小写，次之大写";
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(Pattern.matches(VvReg.SECONDUPPER, "vV1dsd11"));
		System.out.println(Pattern.matches(VvReg.LETTERS, "vVadmin1"));
	}
//	digits: [/^\d+$/, "请输入数字"]
//            ,letters: [/^[a-z]+$/i, "{0}只能输入字母"]
//            ,tel: [/^(?:(?:0\d{2,3}[- ]?[1-9]\d{6,7})|(?:[48]00[- ]?[1-9]\d{6}))$/, "电话格式不正确"]
//            ,mobile: [/^1[3-9]\d{9}$/, "手机号格式不正确"]
//            ,email: [/^(?:[a-z0-9]+[_\-+.]?)*[a-z0-9]+@(?:([a-z0-9]+-?)*[a-z0-9]+\.)+([a-z]{2,})+$/i, "邮箱格式不正确"]
//            ,qq: [/^[1-9]\d{4,}$/, "QQ号格式不正确"]
//            ,date: [/^\d{4}-\d{1,2}-\d{1,2}$/, "请输入正确的日期,例:yyyy-mm-dd"]
//            ,time: [/^([01]\d|2[0-3])(:[0-5]\d){1,2}$/, "请输入正确的时间,例:14:30或14:30:00"]
//            ,ID_card: [/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/, "请输入正确的身份证号码"]
//            ,url: [/^(https?|ftp):\/\/[^\s]+$/i, "网址格式不正确"]
//            ,postcode: [/^[1-9]\d{5}$/, "邮政编码格式不正确"]
//            ,chinese: [/^[\u0391-\uFFE5]+$/, "请输入中文"]
//            ,username: [/^\w{3,12}$/, "请输入3-12位数字、字母、下划线"]
//            ,password: [/^[0-9a-zA-Z_&#$!]{4,16}$/, "密码由4-16位数字、字母组成"]
}
