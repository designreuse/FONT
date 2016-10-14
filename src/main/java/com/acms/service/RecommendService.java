package com.acms.service;

import java.util.List;
import java.util.Map;

import com.acms.entity.Font;
import com.acms.entity.Recommend;

public interface RecommendService {

	void insertSelective(Recommend re) throws Exception;

	List<Recommend> selectByPaging(Map<String, Object> query);

	int countByPaging(Map<String, Object> query);

	void update(Recommend re) throws Exception;

	void delete(Recommend re) throws Exception;

	List<Font> selectFontByPaging(Map<String, Object> query);

	int countFontByPaging(Map<String, Object> query);


}
