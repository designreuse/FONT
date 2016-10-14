package com.acms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.acms.dao.MenuDao;
import com.acms.entity.Menu;

@Repository
public class MenuDaoImpl extends AbstractMyBatisDaoImpl<Menu> implements MenuDao {

	public static final String NAMESPACE = "com.acms.dao.MenuDao";
	
	@Override
	public int deleteByPrimaryKey(Integer menuid) {
		return getSessionTemplate().delete(NAMESPACE + ".deleteByPrimaryKey", menuid);
	}

	@Override
	public int insert(Menu record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(Menu record) {
		return getSessionTemplate().insert(NAMESPACE + ".insertSelective", record);
	}

	@Override
	public Menu selectByPrimaryKey(Integer menuid) throws Exception {
		return getSessionTemplate().selectOne(NAMESPACE + ".selectByPrimaryKey", menuid);
	}

	@Override
	public int updateByPrimaryKeySelective(Menu record) {
		return getSessionTemplate().update(NAMESPACE + ".updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(Menu record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Menu> selectMenuByParentid(Menu menu) {
		return getSessionTemplate().selectList(NAMESPACE + ".selectMenu", menu);
	}

	@Override
	public List<Menu> selectByPaging(Map query) throws Exception {
		return getSessionTemplate().selectList(NAMESPACE + ".selectByPaging", query);
	}

	@Override
	public int countByPaging(Map<String, Object> query) throws Exception {
		return getSessionTemplate().selectOne(NAMESPACE + ".countByPaging", query);
	}

	@Override
	public int getMenuCount(Integer parentid) throws Exception {
		return getSessionTemplate().selectOne(NAMESPACE + ".getMenuCount", parentid);
	}

	@Override
	public int getMaxSequence(Integer parentid) throws Exception {
		return getSessionTemplate().selectOne(NAMESPACE + ".getMaxSequence", parentid);
	}

	@Override
	public int selectMenuCount(Menu menu) throws Exception {
		return getSessionTemplate().selectOne(NAMESPACE + ".selectMenuCount", menu);
	}

	@Override
	public int updateMenuOrder(Menu menu) throws Exception {
		return getSessionTemplate().update(NAMESPACE + ".updateMenuOrder", menu);
	}

	@Override
	public int deleteByParentid(Integer parentid) throws Exception {
		return getSessionTemplate().update(NAMESPACE + ".deleteByParentid", parentid);
	}

	@Override
	public int selectMenuid(Menu menu) {
		return getSessionTemplate().selectOne(NAMESPACE + ".selectMenuid", menu);
	}

	@Override
	public List<Menu> getMenuList(Integer parentid) {
		return getSessionTemplate().selectList(NAMESPACE + ".getMenuList", parentid);
	}

	@Override
	public int getMaxLevel(Integer roleid) {
		return getSessionTemplate().selectOne(NAMESPACE + ".getMaxLevel", roleid);
	}

}
