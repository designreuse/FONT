package com.acms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.acms.dao.FontDao;
import com.acms.entity.Font;
@Repository("fontDao")
public class FontDaoImpl extends AbstractMyBatisDaoImpl<Font> implements FontDao {

	public static final String NAMESPACE ="com.acms.dao.FontDao";
	
	@Override
	public int deleteByPrimaryKey(Integer fontid) {
		return getSessionTemplate().delete(NAMESPACE + ".deleteByPrimaryKey", fontid);
	}

	@Override
	public int insert(Font record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(Font record) {
		return getSessionTemplate().insert(NAMESPACE + ".insertSelective", record);
	}

	@Override
	public Font selectByPrimaryKey(Integer fontid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Font record) {
		return getSessionTemplate().update(NAMESPACE + ".updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(Font record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Font> selectByPaging(Map<String, Object> query) {
		return getSessionTemplate().selectList(NAMESPACE + ".selectByPaging", query);
	}

	@Override
	public int countByPaging(Map<String, Object> query) {
		return getSessionTemplate().selectOne(NAMESPACE + ".countByPaging", query);
	}

	@Override
	public List<Font> getFontExceptUpd(Integer fontid) {
		return getSessionTemplate().selectList(NAMESPACE + ".getFontExceptUpd", fontid);
	}

	@Override
	public int updateOrder(Font f) {
		return getSessionTemplate().update(NAMESPACE + ".updateOrder", f);
	}

	@Override
	public int getFontCount() {
		return getSessionTemplate().selectOne(NAMESPACE + ".getFontCount");
	}

}
