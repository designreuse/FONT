package com.acms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.acms.dao.RotationDao;
import com.acms.entity.Font;
import com.acms.entity.Rotation;
@Repository("rotationDao")
public class RotationDaoImpl extends AbstractMyBatisDaoImpl<Rotation> implements RotationDao {
	
	public static final String NAMESPACE ="com.acms.dao.RotationDao";

	@Override
	public int deleteByPrimaryKey(Integer rotationid) {
		return getSessionTemplate().delete(NAMESPACE + ".deleteByPrimaryKey", rotationid);
	}

	@Override
	public int insert(Rotation record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(Rotation record) {
		return getSessionTemplate().insert(NAMESPACE + ".insertSelective", record);
	}

	@Override
	public Rotation selectByPrimaryKey(Integer rotationid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Rotation record) {
		return getSessionTemplate().update(NAMESPACE + ".updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(Rotation record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Rotation> getRotationExceptUpd(Rotation ro) {
		return getSessionTemplate().selectList(NAMESPACE + ".getRotationExceptUpd", ro);
	}

	@Override
	public int updateOrder(Rotation r) {
		return getSessionTemplate().update(NAMESPACE + ".updateOrder", r);
	}

	@Override
	public List<Rotation> selectByPaging(Map<String, Object> query) {
		return getSessionTemplate().selectList(NAMESPACE + ".selectByPaging", query);
	}

	@Override
	public int countByPaging(Map<String, Object> query) {
		return getSessionTemplate().selectOne(NAMESPACE + ".countByPaging", query);
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
	public int getRotationCount() {
		return getSessionTemplate().selectOne(NAMESPACE + ".getRotationCount");
	}

	@Override
	public int countByFontid(Integer fontid) {
		return getSessionTemplate().selectOne(NAMESPACE + ".countByFontid", fontid);
	}

	@Override
	public Rotation selectByFontid(Integer fontid) {
		return getSessionTemplate().selectOne(NAMESPACE + ".selectByFontid", fontid);
	}

}
