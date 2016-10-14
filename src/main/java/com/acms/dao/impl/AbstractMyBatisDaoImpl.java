package com.acms.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;

import com.acms.dao.AbstractMyBatisDao;

public class AbstractMyBatisDaoImpl<T> implements AbstractMyBatisDao<T>{
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name = "sessionTemplate")
	private SqlSessionTemplate sessionTemplate;

	public SqlSessionTemplate getSessionTemplate() {
		return sessionTemplate;
	}

	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}

	@Override
	public long saveEntity(String key,T obj){
		return sessionTemplate.insert(key, obj);
	}
	
	@Override
	public T getEntity(String key,T obj){
		return sessionTemplate.selectOne(key, obj);
	}

	@Override
	public void deleteEntity(String key, T obj) throws Exception {
		sessionTemplate.delete(key, obj);
	}

	@Override
	public void updateEntity(String key, T obj) throws Exception {
		sessionTemplate.update(key, obj);
	}

	@Override
	@SuppressWarnings("all")
	public List<T> selectList(String key, T obj) throws Exception {
		return (List<T>) sessionTemplate.selectList(key, obj);
	}

	
	@Override
	@SuppressWarnings("all")
	public List<T> selectListByMap(String key, Map paramMap) throws Exception {
		return (List<T>) sessionTemplate.selectList(key, paramMap);
	}

	@Override
	public T getEntity(String key, Map paramMap) throws Exception {
		return sessionTemplate.selectOne(key, paramMap);
	}

	@Override
	public List<Map<String, Object>> selectExport(String key, T obj) {
		return sessionTemplate.selectList(key, obj);
	}
}
