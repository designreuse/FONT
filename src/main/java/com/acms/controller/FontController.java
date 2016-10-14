package com.acms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.acms.cache.SessionUtil;
import com.acms.config.WebConfigLoader;
import com.acms.domain.DataTablesHelper;
import com.acms.domain.DataTablesResult;
import com.acms.domain.JsonResponse;
import com.acms.domain.JsonResponseFactory;
import com.acms.entity.Font;
import com.acms.service.FontService;
import com.acms.util.FileUtils;
import com.acms.util.ImageUploadHandler;
import com.acms.view.VVView;

@Controller
@RequestMapping("/font")
public class FontController extends AbstractController {
	
	private static HashMap<Integer,String> hp = new HashMap<Integer,String>();
	static{
		hp.put(8, "wholerank");
	}
	
	@Resource
	private FontService fontService;
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest req, HttpServletResponse resp) {
		logger.info(">>>>>>>>>>>>>>>进入所有字体管理页>>>>>>>>>>>>>>");
		ModelAndView mv = new VVView("/font/font");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/query")
	public DataTablesResult query(HttpServletRequest req, HttpServletResponse resp,String aoData,String fontname) {	
		logger.info(">>>>>>>>>>>>>>>查询所有字体>>>>>>>>>>>>>>");
		DataTablesResult dtr = new DataTablesResult();
		try {
			if(StringUtils.isNotBlank(aoData)){
				DataTablesHelper.Parameter dp = new DataTablesHelper(aoData).init();
				
				Map<String,Object> query = new HashMap<String,Object>();
				query.put("start", dp.getiDisplayStart());
				query.put("limit", dp.getiDisplayLength());
				query.put("orderName", hp.get(dp.getiSortCol()));
				query.put("orderType", dp.getsSortDir());
				query.put("fontname", fontname);
				List<Font> lst = fontService.selectByPaging(query);//.selectChildreByPrimaryKey(parentid);
				int count = fontService.countByPaging(query);
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
	public JsonResponse add(HttpServletRequest req, HttpServletResponse resp, Font font,
			@RequestParam(value = "fontFile", required = false) MultipartFile fontFile,
			@RequestParam(value = "iconFile", required = false) MultipartFile iconFile,
			@RequestParam(value = "imgFile", required = false) MultipartFile imgFile) {
		
		logger.info("新增字体：" + font);
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			String dir=req.getSession().getServletContext().getRealPath("upload");
			Map<String, String> ossParam = new HashMap<String, String>();
			ossParam.put("context", WebConfigLoader.getConfig().getConfig("vv.cdn.font.context"));
			ossParam.put("bucket", WebConfigLoader.getConfig().getConfig("vv.cdn.font.bucketname"));
			if (fontFile != null) {
				ossParam.put("subDir", WebConfigLoader.getConfig().getConfig("vv.cdn.keyprefix.font.fontfile"));
				String downurl = new ImageUploadHandler().uploadFile(fontFile, dir, ossParam);
				font.setDownurl(downurl);
				font.setFilesize(FileUtils.formetFileSize(fontFile.getSize()));
			}
			if (iconFile != null) {
				ossParam.put("subDir", WebConfigLoader.getConfig().getConfig("vv.cdn.keyprefix.font.icon"));
				String iconurl = new ImageUploadHandler().uploadFile(iconFile, dir, ossParam);
				font.setFonticon(iconurl);
			}
			if (imgFile != null) {
				ossParam.put("subDir", WebConfigLoader.getConfig().getConfig("vv.cdn.keyprefix.font.img"));
				String imgurl = new ImageUploadHandler().uploadFile(imgFile, dir, ossParam);
				font.setFontimg(imgurl);
			}
			font.setUserid(SessionUtil.getUserLogin(req).getUserid());
			Date now = new Date();
			font.setCreatetime(now);
			font.setUpdatetime(now);
			
			fontService.insert(font);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(), e);
		}
		return jr;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public JsonResponse update(HttpServletRequest req, HttpServletResponse resp, Font font,
			@RequestParam(value = "fontFile", required = false) MultipartFile fontFile,
			@RequestParam(value = "iconFile", required = false) MultipartFile iconFile,
			@RequestParam(value = "imgFile", required = false) MultipartFile imgFile) {
		logger.info("编辑字体：" + font);
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			String dir=req.getSession().getServletContext().getRealPath("upload");
			Map<String, String> ossParam = new HashMap<String, String>();
			ossParam.put("context", WebConfigLoader.getConfig().getConfig("vv.cdn.font.context"));
			ossParam.put("bucket", WebConfigLoader.getConfig().getConfig("vv.cdn.font.bucketname"));
			if (fontFile != null) {
				ossParam.put("subDir", WebConfigLoader.getConfig().getConfig("vv.cdn.keyprefix.font.fontfile"));
				String downurl = new ImageUploadHandler().uploadFile(fontFile, dir, ossParam);
				font.setDownurl(downurl);
				font.setFilesize(FileUtils.formetFileSize(fontFile.getSize()));
			}
			if (iconFile != null) {
				ossParam.put("subDir", WebConfigLoader.getConfig().getConfig("vv.cdn.keyprefix.font.icon"));
				String iconurl = new ImageUploadHandler().uploadFile(iconFile, dir, ossParam);
				font.setFonticon(iconurl);
			}
			if (imgFile != null) {
				ossParam.put("subDir", WebConfigLoader.getConfig().getConfig("vv.cdn.keyprefix.font.img"));
				String imgurl = new ImageUploadHandler().uploadFile(imgFile, dir, ossParam);
				font.setFontimg(imgurl);
			}
			font.setUserid(SessionUtil.getUserLogin(req).getUserid());
			Date now = new Date();
			//font.setCreatetime(now);
			font.setUpdatetime(now);
			
			fontService.update(font);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(), e);
		}
		return jr;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public JsonResponse delete(HttpServletRequest req, HttpServletResponse resp, Integer fontid) {
		logger.info("————删除字体————");
		JsonResponse jr = JsonResponseFactory.getSuccessJsonResp();
		try {
			fontService.chainDelete(fontid);
		} catch (Exception e) {
			jr = JsonResponseFactory.getInnerErrorJsonResp(e);
			logger.error(e.getMessage(), e);
		}	
		return jr;
	}
}
