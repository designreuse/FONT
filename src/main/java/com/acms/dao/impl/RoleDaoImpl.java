package com.acms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.acms.dao.RoleDao;
import com.acms.entity.Menu;
import com.acms.entity.TRole;

@Repository("roleDao")
public class RoleDaoImpl extends AbstractMyBatisDaoImpl<TRole> implements RoleDao{

	public static final String NAMESPACE = "com.acms.dao.RoleDao";
	
	@Override
	public TRole select1(int roleid) {
		return getSessionTemplate().selectOne(NAMESPACE + ".select1", roleid);
	}

	@Override
	public List<TRole> selectByPaging(Map<String, Object> query) {
		return getSessionTemplate().selectList(NAMESPACE + ".selectByPaging", query);
	}

	@Override
	public int countByPaging(Map<String, Object> query) {
		return getSessionTemplate().selectOne(NAMESPACE + ".countByPaging", query);
	}

	@Override
	public int add(TRole role) {
		return getSessionTemplate().insert(NAMESPACE + ".add", role);
	}

	@Override
	public TRole select(TRole role) {
		return getSessionTemplate().selectOne(NAMESPACE + ".select", role);
	}

	@Override
	public int selectRoleCount(TRole role) {
		return getSessionTemplate().selectOne(NAMESPACE + ".selectRoleCount", role);
	}

	@Override
	public int delete(int roleid) {
		return getSessionTemplate().delete(NAMESPACE + ".delete", roleid);
	}

	@Override
	public int update(TRole role) {
		return getSessionTemplate().update(NAMESPACE + ".update", role);
	}

}
