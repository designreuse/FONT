package com.acms.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acms.domain.JsonResponse;
import com.acms.domain.JsonResponseFactory;
import com.acms.entity.Menu;
import com.acms.service.RoleMenuService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 角色菜单Controller
 * @author JDM
 *
 */
@Controller
@RequestMapping("/roleMenu")
@SuppressWarnings("all")
public class RoleMenuController extends AbstractController {
	@Resource
	private RoleMenuService roleMenuService; 
	
	@ResponseBody
	@RequestMapping("/menuTree")
	public JSONArray menuTree(Integer roleid) {
		
		JSONArray jsonArr = null;
		try {
/*			List<Menu> lst = roleMenuService.getMenuListByRole(rm);
			if (lst != null && lst.size() != 0) {
				jsonArr = new JSONArray();
				for (Menu m : lst) {
					JSONObject json = new JSONObject();
					json.put("id", m.getMenuid());
					json.put("text", m.getMenuname());
					rm.setParentid(m.getMenuid());
					json.put("children", menuTree(rm));
					jsonArr.add(json);
				}
			}*/
			
			List<Menu> lst = roleMenuService.getLastNodes(roleid);
			if (lst != null && lst.size() != 0) {
				jsonArr = new JSONArray();
				for (Menu m : lst) {
					JSONObject json = new JSONObject();
					json.put("id", m.getMenuid());
					json.put("text", m.getMenuname());
					json.put("children", null);
					jsonArr.add(json);
				}
			}
		} catch (Exception e) {
			logger.error("获取菜单树出错", e);
		}
		return jsonArr;
	}
}
