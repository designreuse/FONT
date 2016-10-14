package com.acms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.acms.dao.RoleMenuDao;
import com.acms.entity.Menu;
import com.acms.entity.RoleMenu;

@Repository
public class RoleMenuDaoImpl extends AbstractMyBatisDaoImpl<RoleMenu> implements RoleMenuDao {
	
	public static final String NAMESPACE = "com.acms.dao.RoleMenuDao";

	@Override
	public int deleteByPrimaryKey(Integer rolemenuid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(RoleMenu record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(RoleMenu record) {
		return getSessionTemplate().insert(NAMESPACE + ".insertSelective", record);
	}

	@Override
	public RoleMenu selectByPrimaryKey(Integer rolemenuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(RoleMenu record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(RoleMenu record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Menu> getMenuListByRole(RoleMenu rm) {
		return getSessionTemplate().selectList(NAMESPACE + ".getMenuListByRole", rm);
	}

	@Override
	public int insertUniq(RoleMenu rm) {
		return getSessionTemplate().insert(NAMESPACE + ".insertUniq", rm);
	}

	@Override
	public int deleteByRoleid(int roleid) {
		return getSessionTemplate().delete(NAMESPACE + ".deleteByRoleid", roleid);
	}

	@Override
	public int deleteByMenuid(int menuid) {
		return getSessionTemplate().delete(NAMESPACE + ".deleteByMenuid", menuid);
	}

	@Override
	public List<Menu> getLastNodes(Integer roleid) {
		return getSessionTemplate().selectList(NAMESPACE + ".getLastNodes", roleid);
	}

}
