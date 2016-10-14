package com.acms.service;

import java.util.List;

import com.acms.entity.Menu;
import com.acms.entity.RoleMenu;

public interface RoleMenuService extends AbstractMyBatisService<RoleMenu>{

    int insertSelective(RoleMenu record);

	public List<Menu> getMenuListByRole(RoleMenu rm);

	public int insertUniq(RoleMenu rm);

	public int deleteByRoleid(int roleid);

	public List<Menu> getLastNodes(Integer roleid);

}
