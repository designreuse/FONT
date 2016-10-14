package com.acms.dao;

import java.util.List;
import java.util.Map;

import com.acms.entity.Font;
import com.acms.entity.Recommend;

public interface RecommendDao {
    int deleteByPrimaryKey(Integer recommendid);

    int insert(Recommend record);

    int insertSelective(Recommend record);

    Recommend selectByPrimaryKey(Integer recommendid);

    int updateByPrimaryKeySelective(Recommend record);

    int updateByPrimaryKey(Recommend record);

	int getRecommendCount(Recommend re);

	List<Recommend> selectByPaging(Map<String, Object> query);

	int countByPaging(Map<String, Object> query);

	List<Recommend> getRecommendExceptUpd(Recommend re);

	int updateOrder(Recommend r);

	List<Font> selectFontByPaging(Map<String, Object> query);

	int countFontByPaging(Map<String, Object> query);

	int countByFontid(Integer fontid);

	Recommend selectByFontid(Integer fontid);
}