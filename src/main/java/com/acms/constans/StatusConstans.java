package com.acms.constans;

/**
 * 状态常量类
 * @author JDM
 * @date 2014-12-15
 */
public class StatusConstans {
	/** 布尔值true */
	public static final int TRUE_STATUS = 1;
	/** 布尔值false */
	public static final int FALSE_STATUS = 2;
	
	/** String布尔值true */
	public static final String TRUE_STR_STATUS = "1";
	/** String布尔值false */
	public static final String FALSE_STR_STATUS = "2";
	
	/** 性别男 */
	public static final int MALE = 1;
	/** 性别女 */
	public static final int FEMALE = 2;
	
	/** 注册类型：系统生成 */
	public static final int REG_TYPE_SYS = 1;
	/** 注册类型：用户注册 */
	public static final int REG_TYPE_USER = 2;
	
	/** 公司类型-公司 */
	public static final int COMPANY_TYPE_COMP = 1;
	/** 公司类型-渠道 */
	public static final int COMPANY_TYPE_CHANNEL = 2;
	/** 公司类型-CP */
	public static final int COMPANY_TYPE_CP = 3;
	
	/** 合作类型-广告主cp */
	public static final int COOPERATION_TYPE_CP = 1;
	/** 合作类型-渠道 */
	public static final int COOPERATION_TYPE_CHANNEL = 2;
	
	/** 起始状态 */
	public static final long BEGIN_STATE = 1;
	
	/** 未发布状态 */
	public static final int NONE_RELEASE_STATE = 1;
	/** 已发布状态 */
	public static final int RELEASED_STATE = 2;
	
	
	/** 刷机工具返回成功状态 */
	public static final String FLASH_TOOL_RESP_SUCCESS = "1";
	/** 刷机工具返回失败状态 */
	public static final String FLASH_TOOL_RESP_ERROR = "0";
	
	/** 个性化定制明细分类 */
	public static final int PCD_COLID_PHONEMAN = 1;
	public static final int PCD_COLID_PHONEMOD = 2;
	public static final int PCD_COLID_ROM = 3;
	public static final int PCD_COLID_CHANNEL = 4;
	public static final int PCD_COLID_ANDROID_VERSION = 5;
	/**  */
	public static final int PCD_OPR_EQUAL = 1;
	public static final int PCD_OPR_GT = 2;
	public static final int PCD_OPR_LT = 3;
	public static final int PCD_OPR_IN = 4;
	public static final int PCD_OPR_NOTIN = 4;
	
	public static final String FLASH_TYPE_SHUAJI = "8";
	public static final String FLASH_TYPE_SHENZHUANG = "2";
	public static final String FLASH_TYPE_QIANZHUANG = "1";
	
	/** 装机指令单*/
	public static long INSTRUCTION_ZHUANGJI = 1;
	/** 刷机指令单*/
	public static long INSTRUCTION_SHUAJI = 2;
	
	/** 推送指定用户 */
	public static final int PUSH_RANGE_SPECIAL = 1;
	/** 推送所有用户 */
	public static final int PUSH_RANGE_ALL = 2;
	/** 推送指定标签用户  */
	public static final int PUSH_RANGE_TAG = 3;
	
	
	/** 推送未提交 */
	public static final int PUSH_STATE_NONESUBMIT = 0;
	/** 推送已提交 */
	public static final int PUSH_STATE_SUBMITED = 1;
	/** 推送已发送  */
	public static final int PUSH_STATE_SENT = 2;
}
