package com.acms.dao;

import java.util.List;

import com.acms.entity.Menu;
import com.acms.entity.RoleMenu;

public interface RoleMenuDao {
    int deleteByPrimaryKey(Integer rolemenuid);

    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    RoleMenu selectByPrimaryKey(Integer rolemenuid);

    int updateByPrimaryKeySelective(RoleMenu record);

    int updateByPrimaryKey(RoleMenu record);

	List<Menu> getMenuListByRole(RoleMenu rm);

	int insertUniq(RoleMenu rm);

	int deleteByRoleid(int roleid);
	
	int deleteByMenuid(int menuid);

	List<Menu> getLastNodes(Integer roleid);
}