package com.acms.entity;

import java.util.Date;

public class Font {
	
	public static final int VALID = 1; // Redis中数据有效
	public static final int INVALID = 0; // Redis中数据无效
	
    private Integer fontid;

    private String fontname;

    private String downurl;
    
    private String filesize;

    private Integer userid;

    private String fonttags;

    private String fontdesc;

    private String fonticon;

    private String fontimg;

    private Date createtime;

    private Date updatetime;
    
    private Integer wholerank;
    
    private String username;
    
    public Integer getFontid() {
        return fontid;
    }

    public void setFontid(Integer fontid) {
        this.fontid = fontid;
    }

    public String getFontname() {
        return fontname;
    }

    public void setFontname(String fontname) {
        this.fontname = fontname == null ? null : fontname.trim();
    }

    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl == null ? null : downurl.trim();
    }

    public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getFonttags() {
        return fonttags;
    }

    public void setFonttags(String fonttags) {
        this.fonttags = fonttags == null ? null : fonttags.trim();
    }

    public String getFontdesc() {
        return fontdesc;
    }

    public void setFontdesc(String fontdesc) {
        this.fontdesc = fontdesc == null ? null : fontdesc.trim();
    }

    public String getFonticon() {
        return fonticon;
    }

    public void setFonticon(String fonticon) {
        this.fonticon = fonticon == null ? null : fonticon.trim();
    }

    public String getFontimg() {
        return fontimg;
    }

    public void setFontimg(String fontimg) {
        this.fontimg = fontimg == null ? null : fontimg.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

	public Integer getWholerank() {
		return wholerank;
	}

	public void setWholerank(Integer wholerank) {
		this.wholerank = wholerank;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}