package com.acms.dao;

import java.util.List;
import java.util.Map;

import com.acms.entity.Font;
import com.acms.entity.Rotation;

public interface RotationDao {
    int deleteByPrimaryKey(Integer rotationid);

    int insert(Rotation record);

    int insertSelective(Rotation record);

    Rotation selectByPrimaryKey(Integer rotationid);

    int updateByPrimaryKeySelective(Rotation record);

    int updateByPrimaryKey(Rotation record);

	List<Rotation> getRotationExceptUpd(Rotation ro);

	int updateOrder(Rotation r);

	List<Rotation> selectByPaging(Map<String, Object> query);

	int countByPaging(Map<String, Object> query);

	List<Font> selectFontByPaging(Map<String, Object> query);

	int countFontByPaging(Map<String, Object> query);

	int getRotationCount();

	int countByFontid(Integer fontid);

	Rotation selectByFontid(Integer fontid);

}