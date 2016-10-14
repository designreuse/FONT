package com.acms.util;

import java.util.ArrayList;
public class TreeNode {
	private long id;
	private long pid;
	private String text;
	private long type;
	private String state;
	private ArrayList<TreeNode> children = new ArrayList<TreeNode>();
	public TreeNode(long id, long pid, String name, String remark,long type) {
		this.id = id;
		this.pid = pid;
		this.text = name;
		this.type=type;
	}

	public void add(TreeNode node) {//递归添加节点
		if (0==node.pid) {
			this.children.add(node);
		} else if (node.pid==this.id) {
			this.children.add(node);
		} else {
			for (TreeNode tmp_node : children) {
				tmp_node.add(node);
			}
		}
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public ArrayList<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<TreeNode> children) {
		this.children = children;
	}
	public long getType() {
		return type;
	}
	public void setType(long type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	
}

