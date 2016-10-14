package com.acms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acms.dao.FontDao;
import com.acms.dao.RecommendDao;
import com.acms.dao.RotationDao;
import com.acms.entity.Font;
import com.acms.entity.Recommend;
import com.acms.entity.Rotation;
import com.acms.service.FontService;
import com.acms.service.RecommendService;
import com.acms.service.RotationService;
import com.acms.util.RedisUtil;

import redis.clients.jedis.Jedis;

@Service("fontService")
public class FontServiceImpl implements FontService {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private FontDao fontDao;
	@Resource
	private RecommendDao recommendDao;
	@Resource
	private RecommendService recommendService;
	@Resource
	private RotationDao rotationDao;
	@Resource
	private RotationService rotationService;

	@Override
	public List<Font> selectByPaging(Map<String, Object> query) {
		return fontDao.selectByPaging(query);
	}

	@Override
	public int countByPaging(Map<String, Object> query) {
		return fontDao.countByPaging(query);
	}
	
	public void setRedisInvalid() throws Exception {
		Jedis jedis = null;
		try {
			// mysql增删改操作对应设置Redis数据失效
			jedis = RedisUtil.getPool().getResource();
			jedis.set("isFontValid", String.valueOf(Font.INVALID));
			jedis.set("isRecommendValid", String.valueOf(Recommend.INVALID));
			jedis.set("isRotationValid", String.valueOf(Rotation.INVALID));
		} catch (Exception e) {
			throw e;
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public void updateOrder (Font font) {
		List<Font> list = fontDao.getFontExceptUpd(font.getFontid());
		for (int i = 0; i < list.size(); i++) {
			Font f = list.get(i);
			if (i < font.getWholerank() -1) {
				f.setWholerank(i + 1);
			} 
			if (i >= font.getWholerank() -1) {
				f.setWholerank(i + 2);
			}
			fontDao.updateOrder(f);
		}
	}
	
	public void deleteOrder (Integer fontid) {
		List<Font> list = fontDao.getFontExceptUpd(fontid);
		for (int i = 0; i < list.size(); i++) {
			Font f = list.get(i);
			f.setWholerank(i + 1);
			fontDao.updateOrder(f);
		}
	}
	
	@Override
	public void insertSelective(Font font) {
		fontDao.insertSelective(font);
	}

	@Override
	public void updateByPrimaryKeySelective(Font font) {
		fontDao.updateByPrimaryKeySelective(font);
	}

	@Override
	public void deleteByPrimaryKey(Integer fontid) {
		fontDao.deleteByPrimaryKey(fontid);
	}

	@Transactional
	@Override
	public void insert(Font font) throws Exception {
		int fontCount = fontDao.getFontCount();
		if (font.getWholerank() > fontCount + 1) {
			font.setWholerank(fontCount + 1);
		}
		fontDao.insertSelective(font);
		updateOrder(font);
		setRedisInvalid();
		
	}

	@Transactional
	@Override
	public void update(Font font) throws Exception {
		int fontCount = fontDao.getFontCount();
		if (font.getWholerank() > fontCount) {
			font.setWholerank(fontCount);
		}
		fontDao.updateByPrimaryKeySelective(font);
		updateOrder(font);
		setRedisInvalid();
		
	}
	
	@Transactional
	@Override
	public void chainDelete(Integer fontid) throws Exception {
		fontDao.deleteByPrimaryKey(fontid);
		deleteOrder(fontid);
		setRedisInvalid();
		if (recommendDao.countByFontid(fontid) != 0) {
			recommendService.delete(recommendDao.selectByFontid(fontid));
		}
		if (rotationDao.countByFontid(fontid) != 0) {
			rotationService.delete(rotationDao.selectByFontid(fontid));
		}
	}
}
