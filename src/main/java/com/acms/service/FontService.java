package com.acms.service;

import java.util.List;
import java.util.Map;

import com.acms.entity.Font;

public interface FontService {

	List<Font> selectByPaging(Map<String, Object> query);

	int countByPaging(Map<String, Object> query);

	void insertSelective(Font font);

	void updateByPrimaryKeySelective(Font font);

	void deleteByPrimaryKey(Integer fontid);
	
	void chainDelete(Integer fontid) throws Exception;

	void insert(Font font) throws Exception;

	void update(Font font) throws Exception;
}
