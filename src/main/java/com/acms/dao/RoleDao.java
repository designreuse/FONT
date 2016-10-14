package com.acms.dao;

import java.util.List;
import java.util.Map;

import com.acms.entity.Menu;
import com.acms.entity.TRole;


public interface RoleDao extends AbstractMyBatisDao<TRole>{

	TRole select1(int roleid);

	List<TRole> selectByPaging(Map<String, Object> query);

	int countByPaging(Map<String, Object> query);

	int add(TRole role);

	TRole select(TRole role);

	int selectRoleCount(TRole role);

	int delete(int roleid);

	int update(TRole role);
}
