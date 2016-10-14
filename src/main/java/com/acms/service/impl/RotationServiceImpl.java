package com.acms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.acms.dao.RotationDao;
import com.acms.entity.Font;
import com.acms.entity.Rotation;
import com.acms.service.RotationService;
import com.acms.util.RedisUtil;

import redis.clients.jedis.Jedis;

@Service("rotationService")
public class RotationServiceImpl implements RotationService {

	@Resource
	private RotationDao rotationDao;
	
	public void setRedisInvalid() throws Exception {
		Jedis jedis = null;
		try {
			// mysql增删改操作对应设置Redis数据失效
			jedis = RedisUtil.getPool().getResource();
			jedis.set("isRotationValid", String.valueOf(Rotation.INVALID));
		} catch (Exception e) {
			throw e;
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public void updateOrder (Rotation ro) {
		List<Rotation> list = rotationDao.getRotationExceptUpd(ro);
		for (int i = 0; i < list.size(); i++) {
			Rotation r = list.get(i);
			if (i < ro.getRank() -1) {
				r.setRank(i + 1);
			} 
			if (i >= ro.getRank() -1) {
				r.setRank(i + 2);
			}
			rotationDao.updateOrder(r);
		}
	}
	
	public void deleteOrder (Rotation ro) {
		List<Rotation> list = rotationDao.getRotationExceptUpd(ro);
		for (int i = 0; i < list.size(); i++) {
			Rotation r = list.get(i);
			r.setRank(i + 1);
			rotationDao.updateOrder(r);
		}
	}

	@Override
	public List<Rotation> selectByPaging(Map<String, Object> query) {
		return rotationDao.selectByPaging(query);
	}

	@Override
	public int countByPaging(Map<String, Object> query) {
		return rotationDao.countByPaging(query);
	}

	@Override
	public List<Font> selectFontByPaging(Map<String, Object> query) {
		return rotationDao.selectFontByPaging(query);
	}

	@Override
	public int countFontByPaging(Map<String, Object> query) {
		return rotationDao.countFontByPaging(query);
	}

	@Override
	public void insertSelective(Rotation ro) throws Exception {
		int count = rotationDao.getRotationCount();
		ro.setRank(count + 1);
		rotationDao.insertSelective(ro);
		setRedisInvalid();
	}

	@Override
	public void update(Rotation ro) throws Exception {
		int count = rotationDao.getRotationCount();
		if (ro.getRank() > count) {
			ro.setRank(count);
		}
		rotationDao.updateByPrimaryKeySelective(ro);
		updateOrder(ro);
		setRedisInvalid(); 
	}

	@Override
	public void delete(Rotation ro) throws Exception {
		rotationDao.deleteByPrimaryKey(ro.getRotationid());
		deleteOrder(ro);
		setRedisInvalid();
	}
}
