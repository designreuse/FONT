package com.acms.domain;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class SearchParameter {

	//private String channelid;
	
	private String appname;
	
	private String packagename;
	
	private Integer sortid;
	
	private Integer sourceid;

	
	public void init(Map<String,Object> query){
		/*if(StringUtils.isNotBlank(channelid)){
			query.put("channelid", channelid);
		}*/
		if(StringUtils.isNotBlank(appname)){
			query.put("appname", appname);
		}
		if(StringUtils.isNotBlank(packagename)){
			query.put("packagename", packagename);
		}
		if(sortid!=null){
			query.put("sortid", sortid);
		}
		if(sourceid!=null){
			query.put("sourceid", sourceid);
		}
	}
	/*public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}*/

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getPackagename() {
		return packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	public Integer getSortid() {
		return sortid;
	}

	public void setSortid(Integer sortid) {
		this.sortid = sortid;
	}

	public Integer getSourceid() {
		return sourceid;
	}

	public void setSourceid(Integer sourceid) {
		this.sourceid = sourceid;
	}
	
	
}
