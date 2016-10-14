package com.acms.util;

import java.util.ArrayList;

public class ModelTree {
	private long modelid;
	private long parentid;
	private String modelname;
	private String icon;
	private String url;
	private ArrayList<ModelTree> children = new ArrayList<ModelTree>();
	
	public ModelTree(long modelid, long parentid, String modelname,String icon,String url) {
		this.modelid = modelid;
		this.parentid = parentid;
		this.modelname = modelname;
		this.icon=icon;
		this.url=url;
	}
	public void add(ModelTree node) {//递归添加节点
		if (0==node.parentid) {
			this.children.add(node);
		} else if (node.parentid==this.modelid) {
			this.children.add(node);
		} else {
			for (ModelTree tmp_node : children) {
				tmp_node.add(node);
			}
		}
	}
	
	
	
	public long getModelid() {
		return modelid;
	}
	public void setModelid(long modelid) {
		this.modelid = modelid;
	}
	public long getParentid() {
		return parentid;
	}
	public void setParentid(long parentid) {
		this.parentid = parentid;
	}
	public String getModelname() {
		return modelname;
	}
	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ArrayList<ModelTree> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<ModelTree> children) {
		this.children = children;
	}
	


	
	
	

}
