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
import com.acms.domain.DataTablesHelper;
import com.acms.domain.DataTablesResult;
import com.acms.domain.JsonResponse;
import com.acms.domain.JsonResponseFactory;
import com.acms.entity.RoleMenu;
import com.acms.entity.TRole;
import com.acms.service.MenuService;
import com.acms.service.RoleMenuService;
import com.acms.service.RoleService;
import com.acms.service.UserInfoService;
import com.acms.view.VVView;

/**
 * 角色Controller
 * @author JDM
 *
 */
@Controller
@RequestMapping("/role")
@SuppressWarnings("all")
public class RoleController extends AbstractController {
	
	// 配合datatables排序
	private static Map<Integer,String> hp = new HashMap<Integer,String>();
	static {
		hp.put(0, "roleid");
	}
	
	@Resource
	private RoleService roleService;
	@Resource
	private MenuService menuService;
	@Resource
	private RoleMenuService roleMenuService;
	@Resource
	private UserInfoService userInfoService;
	
	@ResponseBody
	@RequestMapping("/getRoles")
	public JsonResponse getRoles() {
		logger.info("————获取可选角色————");
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			List<TRole> lst = roleService.selectList();
			jr.setObj(lst);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(), e);
		}
		return jr;
	}
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest req,HttpServletResponse resp){
		ModelAndView mv = new VVView("/role/role_index");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/query")
	public DataTablesResult query(String aoData) {
		
		logger.info("————加载角色信息————");
		
		DataTablesHelper.Parameter dp = new DataTablesHelper(aoData).init(); // 存放分页信息的参数
		DataTablesResult dtr = new DataTablesResult(); // 存放返回信息（如记录和分页信息）
		try {
			Map<String, Object> query = new HashMap<String, Object>();
			query.put("start", dp.getiDisplayStart()); // 分页起始位置
			query.put("limit", dp.getiDisplayLength()); // 每页页数
			query.put("orderName", hp.get(dp.getiSortCol())); // 需排序的列名
			query.put("orderType", dp.getsSortDir()); // 排序方向
			List<TRole> list = roleService.selectByPaging(query);
			for (TRole role : list) {
				Timestamp addtime = role.getAdddate();
				if (addtime != null) {
					role.setFormattime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(addtime.getTime()));
				}
			}
			logger.info("所有角色信息————>" + list);
			int totalCount = roleService.countByPaging(query);
			dtr.setsEcho(dp.getsEcho());
			dtr.setAaData(list);
			dtr.setiTotalRecords(totalCount);
			dtr.setiTotalDisplayRecords(totalCount);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return dtr;
	}
	
	@ResponseBody
	@RequestMapping("/check")
	public int getSpecialtopicCount(TRole role) {
		
		logger.info("检测角色是否已存在：" + role);
		int msg = 0;
		try {
			msg = roleService.selectRoleCount(role);
		} catch (Exception e) {
			logger.error("查询角色出错", e);
		}
		return msg;
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public JsonResponse add(HttpServletRequest req, HttpServletResponse resp, TRole role, 
			@RequestParam("ids[]")Integer[] ids) {
		
		logger.info("新增角色：" + role);
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			// 插入t_role
			role.setCreatorid(SessionUtil.getUserLogin(req).getUserid());
			role.setAdddate(getNow());
			roleService.add(role);
			
			int roleid = roleService.select(role).getRoleid();
			// 插入t_role_menu
			for (int id : ids) {
				if(id > 0) {
					RoleMenu rm = new RoleMenu();
					rm.setRoleid(roleid);
					rm.setMenuid(id);
					roleMenuService.insertUniq(rm);
					int parentid = menuService.selectByPrimaryKey(id).getParentid();
					// 如果父菜单id不为0，同样插入
					if (parentid !=0) {
						rm.setMenuid(parentid);
						roleMenuService.insertUniq(rm);
					}
				}
			}
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(), e);
		}
		return jr;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public JsonResponse delete(Integer roleid) {
		logger.info("删除角色，角色id是：" + roleid);
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			roleService.delete(roleid);
			roleMenuService.deleteByRoleid(roleid);
			userInfoService.deleteByRoleid(roleid);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(), e);
		}
		return jr;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public JsonResponse update(TRole role, @RequestParam("ids[]")Integer[] ids) {
		logger.info("编辑角色：" + role);
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			// 插入t_role
			roleService.update(role);
			
			int roleid = roleService.select(role).getRoleid();
			roleMenuService.deleteByRoleid(roleid);
			// 插入t_role_menu
			for (int id : ids) {
				if(id > 0) {
					RoleMenu rm = new RoleMenu();
					rm.setRoleid(roleid);
					rm.setMenuid(id);
					roleMenuService.insertUniq(rm);
					int parentid = menuService.selectByPrimaryKey(id).getParentid();
					// 如果父菜单id不为0，同样插入
					if (parentid !=0) {
						rm.setMenuid(parentid);
						roleMenuService.insertUniq(rm);
					}
				}
			}
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(), e);
		}
		return jr;
	}
	
/*	@ResponseBody
	@RequestMapping("/update")
	public JsonResponse update(HttpServletRequest req,HttpServletResponse resp,TRole query){
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			roleService.updateEntity("com.acms.dao.RoleDao.update",query);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(),e);
		}
		return jr;
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public JsonResponse add(HttpServletRequest req,HttpServletResponse resp,TRole query){
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		//query.setCreatorid(SessionUtil.getUserLogin(req).getUserid());
		try {
			roleService.saveEntity("com.acms.dao.RoleDao.add",query);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(),e);
		}
		return jr;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public JsonResponse delete(HttpServletRequest req,HttpServletResponse resp,TRole obj){
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			roleService.deleteEntity("com.acms.dao.RoleDao.delete", obj);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(),e);
		}
		return jr;
	}*/
	
	@RequestMapping("/roleDataRange_plugin")
	public ModelAndView roleDataRangePlugin(HttpServletRequest req,HttpServletResponse resp,
			TRole query){
		ModelAndView mv = new VVView("/role/role_dataRange");
		mv.addObject("obj", query);
		return mv;
	}
}
