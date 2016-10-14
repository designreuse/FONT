package com.acms.service;

/**
 * @author LTH
 */
public interface AbstractMyBatisService<T> {
	
	public long saveEntity(String key,T obj) throws Exception;
	
	public void deleteEntity(String key,T obj) throws Exception;
	
	public void updateEntity(String key,T obj) throws Exception;
	
	public T getEntity(String key,T obj) throws Exception;

}
