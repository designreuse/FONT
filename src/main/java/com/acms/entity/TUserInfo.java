package com.acms.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TUserInfo entity.
 */
@Entity
@Table(name="T_USERINFO")
public class TUserInfo implements java.io.Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5234424423062719222L;
	
	// 用户编号
	private Integer userid;
	// 用户名
    private String username;
    // 用户昵称
    private String nikename;
    // 用户图标（头像）
    private String usericon;
    // 本次登录时间
    private Timestamp logintime;
    // 上次登录时间
    private Timestamp lastlogintime;
    // 格式化为字符串的时间
    private String formatetime;
    // 登录IP
    private String loginip;
    // 上次登录IP
    private String lastloginip;
	// 状态
    private long state;
    // 创建人编号
    private long creatorid;
    // 创建时间
    private Timestamp adddate;
    // 密码
    @Transient
    private String password;
    // 用户类型（公司员工，CP用户）
    @Transient
    private int usertype;
    // 登录次数
    private Integer logintimes;
    
    private String rolename;
    
    private Integer roleid;
    
    public TUserInfo(){
    	
    }
    
    @Column(name="USERID", nullable=false, precision=22, scale=0)
    public Integer getUserid() {
        return this.userid;
    }
    
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Column(name="USERNAME", nullable=false, length=50)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name="NIKENAME", length=50)
    public String getNikename() {
        return this.nikename;
    }
    
    public void setNikename(String nikename) {
        this.nikename = nikename;
    }

    @Column(name="USERICON", length=200)
    public String getUsericon() {
        return this.usericon;
    }
    
    public void setUsericon(String usericon) {
        this.usericon = usericon;
    }

    @Column(name="STATE", precision=22, scale=0)
    public long getState() {
        return this.state;
    }
    
    public void setState(long state) {
        this.state = state;
    }

    @Column(name="CREATORID", precision=22, scale=0)
    public long getCreatorid() {
        return this.creatorid;
    }
    
    public void setCreatorid(long creatorid) {
        this.creatorid = creatorid;
    }

    @Column(name="ADDDATE", length=7)
    public Timestamp getAdddate() {
        return this.adddate;
    }
    
    public void setAdddate(Timestamp adddate) {
        this.adddate = adddate;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}
	public String getLoginip() {
		return loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Integer getLogintimes() {
		return logintimes;
	}

	public void setLogintimes(Integer logintimes) {
		this.logintimes = logintimes;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getLastloginip() {
		return lastloginip;
	}

	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}

	public Timestamp getLogintime() {
		return logintime;
	}

	public void setLogintime(Timestamp logintime) {
		this.logintime = logintime;
	}

	public Timestamp getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Timestamp lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public String getFormatetime() {
		return formatetime;
	}

	public void setFormatetime(String formatetime) {
		this.formatetime = formatetime;
	}
}