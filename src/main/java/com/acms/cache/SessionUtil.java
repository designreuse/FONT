package com.acms.cache;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.acms.entity.TUserInfo;

/**从request，session中获取信息*/
@SuppressWarnings("all")
public class SessionUtil {
	
	/**
	 * 获取登录用户
	 * @param request
	 * @return
	 */
	public static TUserInfo getUserLogin(HttpServletRequest request){
		return (TUserInfo)request.getSession().getAttribute("user");
	}
	
	public static TUserInfo getUserLogin(HttpSession session){
		return (TUserInfo)session.getAttribute("user");
	}
}
