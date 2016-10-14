package com.acms.dao;

import java.util.List;
import java.util.Map;

import com.acms.entity.Font;

public interface FontDao {
    int deleteByPrimaryKey(Integer fontid);

    int insert(Font record);

    int insertSelective(Font record);

    Font selectByPrimaryKey(Integer fontid);

    int updateByPrimaryKeySelective(Font record);

    int updateByPrimaryKey(Font record);

	List<Font> selectByPaging(Map<String, Object> query);

	int countByPaging(Map<String, Object> query);

	List<Font> getFontExceptUpd(Integer fontid);

	int updateOrder(Font f);

	int getFontCount();
}