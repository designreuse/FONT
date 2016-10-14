package com.acms.view;

import org.springframework.web.servlet.ModelAndView;

import com.acms.config.VVConfig;
import com.acms.config.WebConfigLoader;
import com.acms.util.PathUtil;

public class VVView extends ModelAndView {
	public VVView() {
		super();
		setProperties();
	}

	public VVView(String viewname) {
		super(viewname);
		setProperties();
	}

	private void setProperties() {
		VVConfig config = WebConfigLoader.getConfig();
		String contentPath = config.getConfig("vv.context");
		String staticcontent = config.getConfig("vv.staticcontent");
		String cdnPath = config.getConfig("vv.cdn.context");
		this.addObject("path", contentPath);
		this.addObject("spath", staticcontent);
		this.addObject("cdnpath", cdnPath);
		this.addObject("httpImg",PathUtil.getDirPath(PathUtil.HTTP_IMG));
	}
}
