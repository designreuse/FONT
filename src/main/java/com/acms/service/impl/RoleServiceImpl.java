package com.acms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acms.dao.RoleDao;
import com.acms.entity.TRole;
import com.acms.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	
	@Resource
	private RoleDao roleDao;

	@Override
	@Transactional
	public long saveEntity(String key, TRole entity) throws Exception {
		roleDao.saveEntity(key, entity);
		return entity.getRoleid();
	}

	@Override
	@Transactional
	public void deleteEntity(String key, TRole entity) throws Exception {
		roleDao.deleteEntity(key, entity);
	}

	@Override
	public TRole getEntity(String key, TRole entity) throws Exception{
		return roleDao.getEntity(key, entity);
	}

	@Override
	@Transactional
	public void updateEntity(String key, TRole entity) throws Exception {
		roleDao.updateEntity(key, entity);
	}

	@Override
	public List<TRole> selectList() throws Exception {
		return roleDao.selectList("com.acms.dao.RoleDao.selectList",null);
	}
	/**
	 * 业务数据:
	 *     Tree树形结构
	 *     角色	根据各种类型,查询角色List R_USERROLE ru, T_ROLE tu
	 * @author majl
	 * @since 0.1
	 */
	public List<TRole> selectRoleTree(TRole query) throws Exception{
		return roleDao.selectList("com.acms.dao.RoleDao.selectRoleTree",query);
	}

	@Override
	public TRole select1(int roleid) {
		return roleDao.select1(roleid);
	}

	@Override
	public List<TRole> selectByPaging(Map<String, Object> query) {
		return roleDao.selectByPaging(query);
	}

	@Override
	public int countByPaging(Map<String, Object> query) {
		return roleDao.countByPaging(query);
	}

	@Override
	public int add(TRole role) {
		return roleDao.add(role);
	}

	@Override
	public TRole select(TRole role) {
		return roleDao.select(role);
	}

	@Override
	public int selectRoleCount(TRole role) {
		return roleDao.selectRoleCount(role);
	}

	@Override
	public int delete(int roleid) {
		return roleDao.delete(roleid);
	}

	@Override
	public int update(TRole role) {
		return roleDao.update(role);
	}
}
