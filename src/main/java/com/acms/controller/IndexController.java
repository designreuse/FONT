package com.acms.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.acms.entity.Menu;
import com.acms.entity.TUserInfo;
import com.acms.service.MenuService;
import com.acms.service.RoleService;
import com.acms.view.VVView;

/**
 * 首页Controller
 */
@Controller
@RequestMapping("/index")
public class IndexController extends AbstractController {
	
	@Resource
	MenuService menuService;
	@Resource
	RoleService roleService;
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest req,HttpServletResponse resp, TUserInfo query){
		
		logger.info("————进入首页————");
		ModelAndView mv = new VVView("/acms");

		try {
			TUserInfo account = (TUserInfo) req.getSession().getAttribute("user");
			Integer roleid = account.getRoleid();
			String username = account.getUsername();
			String rolename = roleService.select1(roleid).getRolename();
			Menu menu0 = new Menu();
			menu0.setParentid(0);
			menu0.setRoleid(roleid);
			List<Menu> menuList = menuService.selectMenuByParentid(menu0);
			int maxlevel = menuService.getMaxLevel(roleid);
			mv.addObject("menuList", menuList);
			mv.addObject("username", username);
			mv.addObject("rolename", rolename);
			mv.addObject("maxlevel", maxlevel);
		} catch (Exception e) {
			logger.error(e);
		}
		return mv;
	}
}
