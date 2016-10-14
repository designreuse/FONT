package com.acms.service;

import java.util.List;
import java.util.Map;

import com.acms.entity.Font;
import com.acms.entity.Rotation;

public interface RotationService {

	List<Rotation> selectByPaging(Map<String, Object> query);

	int countByPaging(Map<String, Object> query);

	List<Font> selectFontByPaging(Map<String, Object> query);

	int countFontByPaging(Map<String, Object> query);

	void insertSelective(Rotation ro) throws Exception;

	void update(Rotation ro) throws Exception;

	void delete(Rotation ro) throws Exception;

}
