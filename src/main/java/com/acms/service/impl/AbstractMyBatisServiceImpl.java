package com.acms.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.acms.dao.AbstractMyBatisDao;
import com.acms.service.AbstractMyBatisService;

@SuppressWarnings(value="all")
public abstract class AbstractMyBatisServiceImpl<T> implements
		AbstractMyBatisService<T> {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	public abstract Class getEntityClass();
	
	@Resource
	private ApplicationContext applicationContext;
	
	@Override
	@Transactional
	public long saveEntity(String key,T entity) throws Exception {
		try {
			return getDao().saveEntity(key,entity);
		} catch (Exception e) {
			logger.error("Error Save Entity:",e);
		}
		return 0;
	}
	
	@Override
	@Transactional
	public void deleteEntity(String key,T entity) {
		try {
			getDao().deleteEntity(key,entity);
		} catch (Exception e) {
			logger.error("Error Delete Entity",e);
		}
	}

	@Override
	public T getEntity(String key, T entity) {
		try {
			return (T) getDao().getEntity(key,entity);
		} catch (Exception e) {
			logger.error("Error Delete Entity",e);
		}
		return entity;
	}

	private AbstractMyBatisDao getDao() {
		 return (AbstractMyBatisDao<T>)applicationContext.getBean(getDaoName(getEntityClass()));
	}
	
	protected String getDaoName(Class clazz){
		String simpleName=clazz.getSimpleName();
		String daoName=simpleName.substring(0,1).toLowerCase()+simpleName.substring(1)+"Dao";
		return daoName;
	}	
	
}
