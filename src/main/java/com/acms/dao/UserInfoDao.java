package com.acms.dao;

import java.util.List;
import java.util.Map;

import com.acms.entity.TUserInfo;


public interface UserInfoDao extends AbstractMyBatisDao<TUserInfo>{

	List<TUserInfo> selectByPaging(Map<String, Object> query);
	
	int countByPaging(Map<String, Object> query);
	
	int getUsernameCount(String username);
	
	int add(TUserInfo user);
	
	int delete(Integer userid);

	int checkOldpwd(TUserInfo user);

	int submitNewpwd(TUserInfo user);
	
	List<TUserInfo> selectBySelective(TUserInfo record);

	int deleteByRoleid(Integer roleid);
}
