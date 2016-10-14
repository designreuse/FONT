package com.acms.dao;

import java.util.List;
import java.util.Map;


public interface AbstractMyBatisDao<T> {
	
	public T getEntity(String key,T obj) throws Exception;
	
	public T getEntity(String key,Map paramMap) throws Exception;
	
	public long saveEntity(String key,T obj) throws Exception;
	
	public void deleteEntity(String key,T obj) throws Exception;
	
	public void updateEntity(String key,T obj) throws Exception;
	
	public List<T> selectList(String key,T obj) throws Exception;
	
	public List<T> selectListByMap(String key,Map paramMap) throws Exception;
	
	public List<Map<String, Object>> selectExport(String key,T obj);
	
}
