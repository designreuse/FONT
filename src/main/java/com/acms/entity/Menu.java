package com.acms.entity;

import java.util.Date;
import java.util.List;

public class Menu {
    private Integer menuid;

    private String menuname;

    private Integer menutype;

    private String uri;

    private Integer visible;

    private Integer parentid;

    private Integer creatorid;

    private Date adddate;

    private String icon;

    private Integer corder;
    
    private List<Menu> childMenus;
    
    private Integer childCount;
    
    private Integer oldOrder;
    
    private Integer roleid;
    
    private Integer menulevel;
    
    public Integer getMenuid() {
        return menuid;
    }

    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname == null ? null : menuname.trim();
    }

    public Integer getMenutype() {
        return menutype;
    }

    public void setMenutype(Integer menutype) {
        this.menutype = menutype;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Integer creatorid) {
        this.creatorid = creatorid;
    }

    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getCorder() {
        return corder;
    }

    public void setCorder(Integer corder) {
        this.corder = corder;
    }

	public List<Menu> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<Menu> childMenus) {
		this.childMenus = childMenus;
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public Integer getOldOrder() {
		return oldOrder;
	}

	public void setOldOrder(Integer oldOrder) {
		this.oldOrder = oldOrder;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public Integer getMenulevel() {
		return menulevel;
	}

	public void setMenulevel(Integer menulevel) {
		this.menulevel = menulevel;
	}

	@Override
	public String toString() {
		return "Menu [menuid=" + menuid + ", menuname=" + menuname + ", menutype=" + menutype + ", uri=" + uri
				+ ", visible=" + visible + ", parentid=" + parentid + ", creatorid=" + creatorid + ", adddate="
				+ adddate + ", icon=" + icon + ", corder=" + corder + ", childMenus=" + childMenus + ", childCount="
				+ childCount + "]";
	}
}