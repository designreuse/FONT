package com.acms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.acms.domain.DataTablesHelper;
import com.acms.domain.DataTablesResult;
import com.acms.domain.JsonResponse;
import com.acms.domain.JsonResponseFactory;
import com.acms.entity.Font;
import com.acms.entity.Rotation;
import com.acms.service.RotationService;
import com.acms.view.VVView;

@Controller
@RequestMapping("/rotation")
public class RotationController extends AbstractController {

	private static HashMap<Integer,String> hp = new HashMap<Integer,String>();
	static{
		hp.put(2, "rank");
	}
	
	@Resource
	private RotationService rotationService;
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest req, HttpServletResponse resp) {
		logger.info(">>>>>>>>>>>>>>>进入轮播字体页面>>>>>>>>>>>>>>");
		ModelAndView mv = new VVView("/font/rotation");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/query")
	public DataTablesResult query(HttpServletRequest req, HttpServletResponse resp,String aoData) {	
		logger.info(">>>>>>>>>>>>>>>查询轮播字体>>>>>>>>>>>>>>");
		DataTablesResult dtr = new DataTablesResult();
		try {
			if(StringUtils.isNotBlank(aoData)){
				DataTablesHelper.Parameter dp = new DataTablesHelper(aoData).init();
				
				Map<String,Object> query = new HashMap<String,Object>();
				query.put("start", dp.getiDisplayStart());
				query.put("limit", dp.getiDisplayLength());
				query.put("orderName", hp.get(dp.getiSortCol()));
				query.put("orderType", dp.getsSortDir());
				List<Rotation> lst = rotationService.selectByPaging(query);
				int count = rotationService.countByPaging(query);
				dtr.setsEcho(dp.getsEcho());
				dtr.setAaData(lst);
				dtr.setiTotalRecords(count);
				dtr.setiTotalDisplayRecords(count);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return dtr;
	}
	
	@ResponseBody
	@RequestMapping("/queryFont")
	public DataTablesResult queryFont(HttpServletRequest req, HttpServletResponse resp,String aoData,String fontname) {	
		logger.info(">>>>>>>>>>>>>>>查询字体>>>>>>>>>>>>>>");
		DataTablesResult dtr = new DataTablesResult();
		try {
			if(StringUtils.isNotBlank(aoData)){
				DataTablesHelper.Parameter dp = new DataTablesHelper(aoData).init();
				
				Map<String,Object> query = new HashMap<String,Object>();
				query.put("start", dp.getiDisplayStart());
				query.put("limit", dp.getiDisplayLength());
				query.put("orderName", "wholerank");
				query.put("orderType", dp.getsSortDir());
				query.put("fontname", fontname);
				List<Font> lst = rotationService.selectFontByPaging(query);
				int count = rotationService.countFontByPaging(query);
				dtr.setsEcho(dp.getsEcho());
				dtr.setAaData(lst);
				dtr.setiTotalRecords(count);
				dtr.setiTotalDisplayRecords(count);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return dtr;
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public JsonResponse add(Rotation ro) {
		
		logger.info("新增轮播字体——");
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			rotationService.insertSelective(ro);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(), e);
		}
		return jr;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public JsonResponse update(HttpServletRequest req, HttpServletResponse resp, Rotation ro) {
		logger.info("修改顺序：" + ro);
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			rotationService.update(ro);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(), e);
		}
		return jr;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public JsonResponse delete(HttpServletRequest req, HttpServletResponse resp, Rotation ro) {
		logger.info("————删除推荐的字体————");
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			rotationService.delete(ro);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(), e);
		}	
		return jr;
	}
}
