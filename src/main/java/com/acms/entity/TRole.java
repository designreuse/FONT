package com.acms.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TRole entity.
 */
@Entity
@Table(name = "T_ROLE", schema = "HZTP")
public class TRole implements java.io.Serializable {

	private static final long serialVersionUID = -1789940837449665573L;
	
	// Fields
	@Id
	private Integer roleid;
	private String rolename;
	private Integer creatorid;
	private Timestamp adddate;
	private String formattime; // 格式化时间
	private Integer state;

	private boolean checked;
	@Transient
	private Integer userid;
	
	@Transient
    private Integer companyid;//公司ID
	
	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public Integer getCreatorid() {
		return creatorid;
	}

	public void setCreatorid(Integer creatorid) {
		this.creatorid = creatorid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	private String username; // 用户名
	


	@Column(name = "ROLENAME", nullable = false, length = 200)
	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Column(name = "ADDDATE", length = 7)
	public Timestamp getAdddate() {
		return this.adddate;
	}

	public void setAdddate(Timestamp adddate) {
		this.adddate = adddate;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFormattime() {
		return formattime;
	}

	public void setFormattime(String formattime) {
		this.formattime = formattime;
	}
		
	
}