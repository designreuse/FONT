package com.acms.entity;

public class Rotation {
    public static final int INVALID = 0; // redis数据失效
    public static final int VALID = 1; // redis数据有效

	private Integer rotationid;

    private Integer fontid;

    private Integer rank;
    
    private String fontname;
    
    private String fontimg;

    public Integer getRotationid() {
        return rotationid;
    }

    public void setRotationid(Integer rotationid) {
        this.rotationid = rotationid;
    }

    public Integer getFontid() {
        return fontid;
    }

    public void setFontid(Integer fontid) {
        this.fontid = fontid;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

	public String getFontname() {
		return fontname;
	}

	public void setFontname(String fontname) {
		this.fontname = fontname;
	}

	public String getFontimg() {
		return fontimg;
	}

	public void setFontimg(String fontimg) {
		this.fontimg = fontimg;
	}
}