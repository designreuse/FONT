package com.acms.entity;

public class Recommend {
	
	public static final int INVALID = 0; // redis数据无效
	public static final int VALID = 1; // redis数据有效

	private Integer recommendid;

    private Integer fontid;

    private Integer recommendtype;

    private Integer rank;
    
    private String fontname;

    public Integer getRecommendid() {
        return recommendid;
    }

    public void setRecommendid(Integer recommendid) {
        this.recommendid = recommendid;
    }

    public Integer getFontid() {
        return fontid;
    }

    public void setFontid(Integer fontid) {
        this.fontid = fontid;
    }

    public Integer getRecommendtype() {
        return recommendtype;
    }

    public void setRecommendtype(Integer recommendtype) {
        this.recommendtype = recommendtype;
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
}