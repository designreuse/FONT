package com.acms.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import com.acms.config.VVConfig;
import com.acms.config.WebConfigLoader;

/** 控制层公用的一些方法 */
public abstract class AbstractController {

	public AbstractController() {
		vvConfig = WebConfigLoader.getConfig();
	}

	/** 日志 */
	protected Logger logger = Logger.getLogger(this.getClass());
	protected VVConfig vvConfig;

	protected void redirectToPage(HttpServletResponse response, String path) {
		String context = vvConfig.getConfig("vv.context");
		try {
			logger.debug("RedirectPath:"+context + path);
			response.sendRedirect(context + path);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 获取request中的ip
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
	
	/**
	 * 获取当前日期时间
	 * @return
	 */
	public static Timestamp getNow() {
		Date date = new Date();
		Timestamp timeStamp = new Timestamp(date.getTime());
		return timeStamp;
	}

}
