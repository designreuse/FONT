package com.acms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acms.dao.RecommendDao;
import com.acms.entity.Font;
import com.acms.entity.Recommend;
import com.acms.service.RecommendService;
import com.acms.util.RedisUtil;

import redis.clients.jedis.Jedis;

@Service("recommendService")
public class RecommendServiceImpl implements RecommendService {

	@Resource
	private RecommendDao recommendDao;
	
	public void setRedisInvalid() throws Exception {
		Jedis jedis = null;
		try {
			// mysql增删改操作对应设置Redis数据失效
			jedis = RedisUtil.getPool().getResource();
			jedis.set("isRecommendValid", String.valueOf(Recommend.INVALID));
		} catch (Exception e) {
			throw e;
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public void updateOrder (Recommend re) {
		List<Recommend> list = recommendDao.getRecommendExceptUpd(re);
		for (int i = 0; i < list.size(); i++) {
			Recommend r = list.get(i);
			if (i < re.getRank() -1) {
				r.setRank(i + 1);
			} 
			if (i >= re.getRank() -1) {
				r.setRank(i + 2);
			}
			recommendDao.updateOrder(r);
		}
	}
	
	public void deleteOrder (Recommend re) {
		List<Recommend> list = recommendDao.getRecommendExceptUpd(re);
		for (int i = 0; i < list.size(); i++) {
			Recommend r = list.get(i);
			r.setRank(i + 1);
			recommendDao.updateOrder(r);
		}
	}

	@Transactional
	@Override
	public void insertSelective(Recommend re) throws Exception {
		int count = recommendDao.getRecommendCount(re);
		re.setRank(count + 1);
		recommendDao.insertSelective(re);
		setRedisInvalid();
	}

	@Override
	public List<Recommend> selectByPaging(Map<String, Object> query) {
		return recommendDao.selectByPaging(query);
	}

	@Override
	public int countByPaging(Map<String, Object> query) {
		return recommendDao.countByPaging(query);
	}

	@Transactional
	@Override
	public void update(Recommend re) throws Exception {
		int count = recommendDao.getRecommendCount(re);
		if (re.getRank() > count) {
			re.setRank(count);
		}
		recommendDao.updateByPrimaryKeySelective(re);
		updateOrder(re);
		setRedisInvalid(); 
	}

	@Transactional
	@Override
	public void delete(Recommend re) throws Exception {
		recommendDao.deleteByPrimaryKey(re.getRecommendid());
		deleteOrder(re);
		setRedisInvalid();
	}

	@Override
	public List<Font> selectFontByPaging(Map<String, Object> query) {
		return recommendDao.selectFontByPaging(query);
	}

	@Override
	public int countFontByPaging(Map<String, Object> query) {
		return recommendDao.countFontByPaging(query);
	}

}
