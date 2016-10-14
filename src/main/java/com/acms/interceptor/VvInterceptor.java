package com.acms.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.acms.cache.SessionUtil;
import com.acms.domain.JsonResponse;
import com.acms.domain.JsonResponseFactory;
import com.alibaba.fastjson.JSON;

/**
 * SpringMVC拦截器，用于验证是否登录、登录超时以及权限
 * */
public class VvInterceptor implements HandlerInterceptor {

	private Logger logger = Logger.getLogger(this.getClass());

	private static final String[] ignoreUrlArray = { "/index", "/user/login",
			"/user/tologin", "/user/logout", "/user/toChangePwd",
			"/user/changepwd" };

	/**
	 * 用于拦截的Session key
	 */
	private String sessionKey;
	/**
	 * 拦截后重定向的url
	 */
	private String redirectUrl;
	/**
	 * 存入session中用户页面跳转回原访问页面的key
	 */
	private String requestUrlKey;
	/**
	 * true:拦截后先弹窗再跳转,false:不弹窗提示，直接跳转
	 */
	private boolean inflag = false;

	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse resp, Object obj, Exception ex)
			throws Exception {

	}

	public void postHandle(HttpServletRequest req, HttpServletResponse resp,
			Object obj, ModelAndView mv) throws Exception {

	}

	/**
	 * 拦截器主处理方法，返回true继续进行下一个拦截，否则请求终止
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (!isAuthorizedRequest(request)) {//未登录直接跳转
			// 将访问url存入session中
			putRequestUrl(request);
			redirectLoginPage(request, response);
			// 如果是ajax请求，直接返回
			if (!isHttpRequest(request)) {
				return false;
			}
		} else {
			if (SessionUtil.getUserLogin(request).getUsername().equals("admin")) {
				return true;
			}
			// 非管理员判断是否有访问权限
			String requestUrl = request.getRequestURI().substring(
					request.getRequestURI().indexOf("/", 1));
			for (String ignoreUrl : ignoreUrlArray) { // 如果是忽略url直接返回true
				if (ignoreUrl.equals(requestUrl)) {
					return true;
				}
			}
			if (isAccessedRequest(request, requestUrl)) {
				return true;
			} else {
				forbiddenRedirect(request, response);
			}
		}
		return false;
	}

	/**
	 * 验证是否登录
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	protected boolean isAuthorizedRequest(HttpServletRequest request)
			throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}
		Object sessionObject = session.getAttribute(getSessionKey());
		return isValidSessionObject(sessionObject);
	}

	/**
	 * 验证请求的url是否有权限
	 * @param request
	 * @param requestUrl
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@SuppressWarnings(value="all")
	protected boolean isAccessedRequest(HttpServletRequest request,
			String requestUrl) throws IOException, ServletException {
/*		HttpSession session = request.getSession(false);
		boolean hasAccess = false;
		if (session == null) {
			return false;
		}
		List<TModel> userModelList = (List<TModel>) session.getAttribute("userModelList");
		if (userModelList == null) {
			return hasAccess;
		} else {
			if (request.getRequestURI().endsWith("index.jsp")) {
				for (TModel tModel : userModelList) {
					if (tModel != null && tModel.getUrl() != null
							&& tModel.getUrl().equals(requestUrl)) {
						hasAccess = true;
					}
				}
			} else {
				if (isValidRequest(request))
					hasAccess = true;
			}
		}
		return hasAccess;*/
		return true;
	}

	/**
	 * 验证session对象是否存在
	 * @param sessionObject
	 * @return
	 */
	protected boolean isValidSessionObject(Object sessionObject) {
		return sessionObject != null;
	}

	/**
	 * 判断是正常的request还是ajax请求 true:正常请求 false:ajax请求
	 * 
	 * @param request
	 * @return
	 */
	public boolean isHttpRequest(HttpServletRequest request) {
		// 如果是ajax请求响应头会有，x-requested-with；
		if (request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase(
						"XMLHttpRequest")) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isValidRequest(HttpServletRequest request) {
		if (request.getHeader("Referer") != null
				&& request.getHeader("Referer").contains(
						request.getContextPath())) {
			return true;
		}
		return false;
	}

	/**
	 * 未登录用户重定向到登录页面
	 * 
	 * @param request
	 * @param response
	 */
	public void redirectLoginPage(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head></head>");
			out.println("<body><script>");
			out.println("var wnd=this.window;");
			out.println("while(1){");
			out.println("if(wnd==wnd.parent){");
			out.println("break;");
			out.println("}else");
			out.println("wnd=wnd.parent;");
			out.println("}");
			out.println("wnd.location.href='"
					+ ((HttpServletRequest) request).getContextPath() + "/"
					+ redirectUrl + "'");
			out.println("</script>");
			out.println("</body></html>");
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	public void forbiddenRedirect(HttpServletRequest request,
			ServletResponse response) {
		logger.error("无访问权限");
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			if (!isHttpRequest(request)) {
				JsonResponse jr = JsonResponseFactory.getNoPRIJsonResp();
				out.println(JSON.toJSON(jr));
			} else {
				out.println("<html><head></head>");
				out.println("<body><script>");
				// out.println("alert('');");
				out.println(" setTimeout(function(){window.location = \""
						+ request.getContextPath() + "\"}, 3000);");
				out.println("</script>");
				out.println("<h1>无权限的非法操作！</h1>");
				out.println("</body></html>");
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/** 获取重定向url，并将它存入session中 */
	public String putRequestUrl(HttpServletRequest request) {
		// 获取请求地址，存入session
		String requestUrl = request.getRequestURL().toString();
		// 获取request中的参数，将参数拼装
		Enumeration<String> e = request.getParameterNames();
		int i = 0;
		while (e.hasMoreElements()) {
			String param = e.nextElement();
			if (i == 0) {
				requestUrl = requestUrl + "?" + param + "="
						+ request.getParameter(param);
			} else {
				requestUrl = requestUrl + "&" + param + "="
						+ request.getParameter(param);
			}
			i++;
		}
		request.getSession().setAttribute(requestUrlKey, requestUrl);
		return requestUrl;
	}

	public boolean isInflag() {
		return inflag;
	}

	public void setInflag(boolean inflag) {
		this.inflag = inflag;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getRequestUrlKey() {
		return requestUrlKey;
	}

	public void setRequestUrlKey(String requestUrlKey) {
		this.requestUrlKey = requestUrlKey;
	}
}
