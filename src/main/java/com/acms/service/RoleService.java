package com.acms.service;

import java.util.List;
import java.util.Map;

import com.acms.entity.TRole;

public interface RoleService extends AbstractMyBatisService<TRole>{

	List<TRole> selectList() throws Exception;
	
	/**
	 * 业务数据:
	 *     Tree树形结构
	 *     角色	根据各种类型,查询角色List R_USERROLE ru, T_ROLE tu
	 * @author majl
	 * @since 0.1
	 */
	public List<TRole> selectRoleTree(TRole query) throws Exception;
	
	TRole select1(int roleid);

	List<TRole> selectByPaging(Map<String, Object> query);

	int countByPaging(Map<String, Object> query);

	int add(TRole role);

	TRole select(TRole role);

	int selectRoleCount(TRole role);

	int delete(int roleid);

	int update(TRole role);
}
