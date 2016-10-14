package com.acms.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.acms.cache.SessionUtil;
import com.acms.constans.StatusConstans;
import com.acms.domain.DataTablesHelper;
import com.acms.domain.DataTablesResult;
import com.acms.domain.JsonResponse;
import com.acms.domain.JsonResponseFactory;
import com.acms.entity.TUserInfo;
import com.acms.service.RoleService;
import com.acms.service.UserInfoService;
import com.acms.view.VVView;

/**
 * 登录用户Controller
 * @author LTH
 */
@Controller
@RequestMapping("/user")
@SuppressWarnings("all")
public class UserInfoController extends AbstractController {
	
	// 配合datatables排序
	private static Map<Integer,String> hp = new HashMap<Integer,String>();
	static {
		hp.put(0, "userid");
	}

	@Resource
	private UserInfoService userInfoService;
	@Resource
	private RoleService roleService;

	@RequestMapping("/tologin")
	public ModelAndView tologin(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mv = new VVView("index");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/login")
	public JsonResponse login(HttpServletRequest req,HttpServletResponse resp, TUserInfo user){
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		user.setLoginip(getIpAddr(req));
		try {
			user = userInfoService.login(user);
			if (null != user) {
				// TODO 查询用户权限和菜单
				req.getSession().setAttribute("user", user);
			} else {
				jr = JsonResponseFactory.getWrongPwdJsonResp(); 
			}
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(),e);
		}
		return jr;
	}
	
	@RequestMapping("/usermng")
	public ModelAndView manage() {
		ModelAndView mv = new VVView("/user/usermng");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/query")
	public DataTablesResult query(String aoData, Integer roleid, String name) {
		
		logger.info("加载用户信息————>查询信息为：roleid=" + roleid + ", name=" + name);
		
		DataTablesHelper.Parameter dp = new DataTablesHelper(aoData).init(); // 存放分页信息的参数
		DataTablesResult dtr = new DataTablesResult(); // 存放返回信息（如记录和分页信息）
		try {
			Map<String, Object> query = new HashMap<String, Object>();
			query.put("start", dp.getiDisplayStart()); // 分页起始位置
			query.put("limit", dp.getiDisplayLength()); // 每页页数
			query.put("orderName", hp.get(dp.getiSortCol())); // 需排序的列名
			query.put("orderType", dp.getsSortDir()); // 排序方向
			query.put("roleid", roleid); // 角色ID
			query.put("name", name); // 查询名称
			List<TUserInfo> lst = userInfoService.selectByPaging(query);
			for (TUserInfo tUserInfo : lst) {
				Timestamp lastlogintime = tUserInfo.getLastlogintime();
				if (lastlogintime != null) {
					tUserInfo.setFormatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(lastlogintime.getTime()));
				}
			}
			logger.info("所有用户信息————>" + lst);
			int totalCount = userInfoService.countByPaging(query);
			dtr.setsEcho(dp.getsEcho());
			dtr.setAaData(lst);
			dtr.setiTotalRecords(totalCount);
			dtr.setiTotalDisplayRecords(totalCount);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return dtr;
	}
	
	@ResponseBody
	@RequestMapping("/check")
	public int getUsernameCount(String username) {
		
		logger.info("检测账号是否已存在，账号：" + username);
		int msg = 0;
		try {
			msg = userInfoService.getUsernameCount(username);
		} catch (Exception e) {
			logger.error("查询账号数出错", e);
		}
		return msg;
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public JsonResponse add(HttpServletRequest req,HttpServletResponse resp, TUserInfo user, 
			@RequestParam(value = "md5password", required = true) String password) {
		
		logger.info("新增用户：" + user);
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			user.setPassword(password);
			user.setAdddate(getNow());
			user.setLogintimes(0);
			// 创建用户
			TUserInfo creator = (TUserInfo) req.getSession().getAttribute("user");
			user.setCreatorid(creator.getUserid());
			user.setUsertype(1);
			userInfoService.add(user);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(), e);
		}
		return jr;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public JsonResponse update(TUserInfo query){
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			userInfoService.updateEntity(query);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(),e);
		}
		return jr;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public JsonResponse delete(Integer userid){
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			userInfoService.delete(userid);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(),e);
		}
		return jr;
	}
	
	@RequestMapping("/changepwd")
	public ModelAndView changepwd(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mv = new VVView("changepwd");
		TUserInfo account = (TUserInfo) req.getSession().getAttribute("user");
		mv.addObject("user", account);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/checkOldpwd")
	public int checkOldpwd(TUserInfo user){
		int msg = 0;
		try {
			msg = userInfoService.checkOldpwd(user);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return msg;
	}
	
	@ResponseBody
	@RequestMapping("/submitNewpwd")
	public JsonResponse submitNewpwd(TUserInfo user){
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			userInfoService.submitNewpwd(user);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(),e);
		}
		return jr;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mv = new VVView("index");
		req.getSession().invalidate();
		return mv;
	}
	
}
