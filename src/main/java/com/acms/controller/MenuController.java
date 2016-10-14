package com.acms.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.acms.cache.SessionUtil;
import com.acms.domain.DataTablesHelper;
import com.acms.domain.DataTablesResult;
import com.acms.domain.JsonResponse;
import com.acms.domain.JsonResponseFactory;
import com.acms.entity.Menu;
import com.acms.entity.RoleMenu;
import com.acms.service.MenuService;
import com.acms.service.RoleMenuService;
import com.acms.view.VVView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 菜单管理controller
 * 
 * @author 白鑫 2016年7月6日 下午3:11:11
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends AbstractController {

	private static Logger logger = Logger.getLogger(MenuController.class);

	// 配合datatables排序
	private static Map<Integer, String> hp = new HashMap<Integer, String>();
	static {
		hp.put(0, "corder");
	}

	@Resource
	MenuService menuService;

	@Resource
	RoleMenuService roleMenuService;

	@RequestMapping("/index")
	public ModelAndView index(Integer menuid, String menuname, String breadcrumb, Integer menulevel) {

		logger.info("————进入菜单管理页面————");
		ModelAndView mv = new VVView("/menu/menu");

		try {
			if (StringUtils.isNotBlank(menuname))
				menuname = URLDecoder.decode(menuname, "UTF-8");
			if (StringUtils.isNotBlank(breadcrumb))
				breadcrumb = URLDecoder.decode(breadcrumb, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		// 默认id为0
		mv.addObject("parentid", menuid == null ? 0 : menuid);
		mv.addObject("menuname", menuname);
		mv.addObject("menulevel", menulevel == null ? 1 : (menulevel + 1));
		if(menuid == null || menuid ==0){
			mv.addObject("breadcrumb","主页;系统设置;菜单管理");
		}else{
			mv.addObject("breadcrumb",breadcrumb.concat(";【"+menuname + "】"));
		}
		return mv;
	}
	
	@RequestMapping("/back")
	public ModelAndView back(Integer id,String breadcrumb, Integer menulevel) {
		logger.info(">>>>>>>>>>>>>>>菜单界面返回>>>>>>>>>>>>>>");
		ModelAndView mv = new VVView("/menu/menu");
		Menu m = null;
		try {
			m = menuService.selectByPrimaryKey(id);
			if(StringUtils.isNotBlank(breadcrumb))
				breadcrumb = URLDecoder.decode(breadcrumb, "UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		// 默认id为0
		mv.addObject("parentid", (m == null || m.getParentid() == null) ? 0 : m.getParentid());
		mv.addObject("parentname", (m == null || m.getMenuname() == null) ? "" : m.getMenuname());
		mv.addObject("menulevel", (menulevel - 1));
		mv.addObject("breadcrumb",breadcrumb.substring(0, breadcrumb.lastIndexOf(";")));
		return mv;
	}

	@RequestMapping("/childMenu")
	public ModelAndView index(Integer menuid, String menuname) {

		logger.info("————进入子菜单页面————");
		ModelAndView mv = new VVView("/menu/childMenu");
		try {
			String decode = URLDecoder.decode(menuname, "UTF-8");
			mv.addObject("menuname", decode);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		mv.addObject("parentid", menuid);
		return mv;
	}

	@ResponseBody
	@RequestMapping("/query")
	public DataTablesResult query(String aoData, Integer parentid, String menuname) {

		logger.info("加载菜单————>查询信息为：menuname=" + menuname);

		DataTablesHelper.Parameter dp = new DataTablesHelper(aoData).init(); // 存放分页信息的参数
		DataTablesResult dtr = new DataTablesResult(); // 存放返回信息（如记录和分页信息）
		try {
			Map<String, Object> query = new HashMap<String, Object>();
			query.put("start", dp.getiDisplayStart()); // 分页起始位置
			query.put("limit", dp.getiDisplayLength()); // 每页页数
			query.put("orderName", hp.get(dp.getiSortCol())); // 需排序的列名
			query.put("orderType", dp.getsSortDir()); // 排序方向
			query.put("parentid", parentid); // 父菜单id
			query.put("menuname", menuname); // 菜单名称
			List<Menu> list = menuService.selectByPaging(query);
			logger.info("父菜单信息————>" + list);
			for (Menu menu : list) {
				int childCount = menuService.getMenuCount(menu.getMenuid());
				menu.setChildCount(childCount);
			}
			int totalCount = menuService.countByPaging(query);
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
	@RequestMapping("/getMaxSeq")
	public JsonResponse getMaxSequence(Integer parentid) {

		logger.info("————获取菜单最大排序数————");
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			if (menuService.getMenuCount(parentid) > 0) {
				int max = menuService.getMaxSequence(parentid);
				jr.setObj(max);
			} else {
				jr.setObj(0);
			}
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(), e);
		}
		return jr;
	}

	@ResponseBody
	@RequestMapping("/check")
	public int getSpecialtopicCount(Menu menu) {

		logger.info("检测菜单是否已存在：" + menu);
		int msg = 0;
		try {
			msg = menuService.selectMenuCount(menu);
		} catch (Exception e) {
			logger.error("查询父菜单数出错", e);
		}
		return msg;
	}

	@ResponseBody
	@RequestMapping("/add")
	public JsonResponse add(HttpServletRequest req, HttpServletResponse resp, Menu menu) {

		logger.info("新增菜单：" + menu);
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			Date now = new Date();
			menu.setAdddate(now);
			menu.setVisible(1);
			menu.setCreatorid(SessionUtil.getUserLogin(req).getUserid());

			if (menuService.getMenuCount(menu.getParentid()) > 0) {
				int sequence = menu.getCorder();
				int maxseq = (int) menuService.getMaxSequence(menu.getParentid());
				if (sequence <= maxseq) {
					for (int i = maxseq; i >= sequence; i--) {
						Menu menu1 = new Menu();
						menu1.setOldOrder(i);
						menu1.setCorder(i + 1);
						menu1.setParentid(menu.getParentid());
						menuService.updateMenuOrder(menu1);
					}
				}
			}
			menuService.insertSelective(menu);

			RoleMenu rm = new RoleMenu();
			rm.setMenuid(menuService.selectMenuid(menu));
			rm.setRoleid(SessionUtil.getUserLogin(req).getRoleid());
			roleMenuService.insertSelective(rm);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(), e);
		}
		return jr;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public JsonResponse delete(Integer menuid, Integer parentid) {
		logger.info("删除菜单，菜单id是：" + menuid);
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			int deleteSeq = menuService.selectByPrimaryKey(menuid).getCorder();
			int maxSeq = menuService.getMaxSequence(parentid);

			menuService.batchDelete(menuid);
			//menuService.deleteByPrimaryKey(menuid);
			//menuService.deleteByParentid(menuid);

			// 更新其他应用的排序
			for (int i = deleteSeq; i < maxSeq; i++) {
				Menu menu = new Menu();
				menu.setOldOrder(i + 1);
				menu.setCorder(i);
				menu.setParentid(parentid);
				menuService.updateMenuOrder(menu);
			}
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(), e);
		}
		return jr;
	}

	@ResponseBody
	@RequestMapping("/update")
	public JsonResponse update(Menu menu, Integer parentid) {
		logger.info("编辑父菜单：" + menu);
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			// 更新前的排序
			int oldsequence = menuService.selectByPrimaryKey(menu.getMenuid()).getCorder();
			// 更新后的排序
			int newsequence = menu.getCorder();

			// 更新其他父菜单的顺序
			if (oldsequence != newsequence) {
				Menu menu1 = new Menu();
				menu1.setOldOrder(oldsequence);
				menu1.setCorder(0);
				menu1.setParentid(parentid);
				menuService.updateMenuOrder(menu1);

				if (oldsequence > newsequence) {
					// 前移
					int offset = oldsequence - newsequence;
					for (int i = 0; i < offset; i++) {
						menu1.setOldOrder(oldsequence - i - 1);
						menu1.setCorder(oldsequence - i);
						menu1.setParentid(parentid);
						menuService.updateMenuOrder(menu1);
					}
				} else if (oldsequence < newsequence) {
					// 后移
					int offset = newsequence - oldsequence;
					for (int i = 0; i < offset; i++) {
						menu1.setOldOrder(oldsequence + i + 1);
						menu1.setCorder(oldsequence + i);
						menu1.setParentid(parentid);
						menuService.updateMenuOrder(menu1);
					}
				}
			}
			menuService.updateByPrimaryKeySelective(menu);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(), e);
		}
		return jr;
	}

	/**
	 * 获取完整菜单树
	 * 
	 * @param menu
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/menuTree")
	public JSONArray menuTree(Integer parentid) {

		JSONArray jsonArr = null;
		try {
			List<Menu> lst = menuService.getMenuList(parentid);
			if (lst != null && lst.size() != 0) {
				jsonArr = new JSONArray();
				for (Menu m : lst) {
					JSONObject json = new JSONObject();
					json.put("id", m.getMenuid());
					json.put("text", m.getMenuname());
					json.put("children", menuTree(m.getMenuid()));
					jsonArr.add(json);
				}
			}

		} catch (Exception e) {
			logger.error("获取菜单树出错", e);
		}
		return jsonArr;
	}

}
