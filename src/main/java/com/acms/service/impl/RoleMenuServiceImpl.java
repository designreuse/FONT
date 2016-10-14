package com.acms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.acms.dao.RoleMenuDao;
import com.acms.entity.Menu;
import com.acms.entity.RoleMenu;
import com.acms.service.RoleMenuService;

@Service("roleMenuService")
public class RoleMenuServiceImpl implements RoleMenuService {
	
	@Resource
	private RoleMenuDao roleMenuDao;

	@Override
	public int insertSelective(RoleMenu record) {
		return roleMenuDao.insertSelective(record);
	}

	@Override
	public List<Menu> getMenuListByRole(RoleMenu rm) {
		return roleMenuDao.getMenuListByRole(rm);
	}

	@Override
	public int insertUniq(RoleMenu rm) {
		return roleMenuDao.insertUniq(rm);
	}

	@Override
	public int deleteByRoleid(int roleid) {
		return roleMenuDao.deleteByRoleid(roleid);
	}

	@Override
	public List<Menu> getLastNodes(Integer roleid) {
		return roleMenuDao.getLastNodes(roleid);
	}

	@Override
	public long saveEntity(String key, RoleMenu obj) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteEntity(String key, RoleMenu obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEntity(String key, RoleMenu obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RoleMenu getEntity(String key, RoleMenu obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
