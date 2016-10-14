package com.acms.entity;

public class RoleMenu {
    private Integer rolemenuid;

    private Integer roleid;

    private Integer menuid;
    
    private Integer parentid; // 菜单表父菜单ID

    public Integer getRolemenuid() {
        return rolemenuid;
    }

    public void setRolemenuid(Integer rolemenuid) {
        this.rolemenuid = rolemenuid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getMenuid() {
        return menuid;
    }

    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
    }

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
}