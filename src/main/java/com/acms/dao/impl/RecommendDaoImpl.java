package com.acms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.acms.dao.RecommendDao;
import com.acms.entity.Font;
import com.acms.entity.Recommend;

@Repository("recommendDao")
public class RecommendDaoImpl extends AbstractMyBatisDaoImpl<Recommend> implements RecommendDao {
	
	public static final String NAMESPACE ="com.acms.dao.RecommendDao";

	@Override
	public int deleteByPrimaryKey(Integer recommendid) {
		return getSessionTemplate().delete(NAMESPACE + ".deleteByPrimaryKey", recommendid);
	}

	@Override
	public int insert(Recommend record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(Recommend record) {
		return getSessionTemplate().insert(NAMESPACE + ".insertSelective", record);
	}

	@Override
	public Recommend selectByPrimaryKey(Integer recommendid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Recommend record) {
		return getSessionTemplate().update(NAMESPACE + ".updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(Recommend record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRecommendCount(Recommend re) {
		return getSessionTemplate().selectOne(NAMESPACE + ".getRecommendCount", re);
	}

	@Override
	public List<Recommend> selectByPaging(Map<String, Object> query) {
		return getSessionTemplate().selectList(NAMESPACE + ".selectByPaging", query);
	}

	@Override
	public int countByPaging(Map<String, Object> query) {
		return getSessionTemplate().selectOne(NAMESPACE + ".countByPaging", query);
	}

	@Override
	public List<Recommend> getRecommendExceptUpd(Recommend re) {
		return getSessionTemplate().selectList(NAMESPACE + ".getRecommendExceptUpd", re);
	}

	@Override
	public int updateOrder(Recommend r) {
		return getSessionTemplate().update(NAMESPACE + ".updateOrder", r);
	}

	@Override
	public List<Font> selectFontByPaging(Map<String, Object> query) {
		return getSessionTemplate().selectList(NAMESPACE + ".selectFontByPaging", query);
	}

	@Override
	public int countFontByPaging(Map<String, Object> query) {
		return getSessionTemplate().selectOne(NAMESPACE + ".countFontByPaging", query);
	}

	@Override
	public int countByFontid(Integer fontid) {
		return getSessionTemplate().selectOne(NAMESPACE + ".countByFontid", fontid);
	}

	@Override
	public Recommend selectByFontid(Integer fontid) {
		return getSessionTemplate().selectOne(NAMESPACE + ".selectByFontid", fontid);
	}

}
