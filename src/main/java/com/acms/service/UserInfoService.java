package com.acms.service;

import java.util.List;
import java.util.Map;

import com.acms.entity.TUserInfo;

public interface UserInfoService extends AbstractMyBatisService<TUserInfo>{
	
	List<TUserInfo> selectByPaging(Map<String, Object> query);
	
	int countByPaging(Map<String, Object> query);
	
	int getUsernameCount(String username);
	
	int add(TUserInfo user);

	void updateEntity(TUserInfo query) throws Exception;
	
	TUserInfo login(TUserInfo userLogin) throws Exception;

	List<TUserInfo> selectList(Map map) throws Exception;

	List<TUserInfo> selectList(String string, TUserInfo query) throws Exception;

	void updateUserCenter(TUserInfo query) throws Exception;
	
	int delete(Integer userid);

	int checkOldpwd(TUserInfo user);

	int submitNewpwd(TUserInfo user);
	
	List<TUserInfo> selectBySelective(TUserInfo record);

	int deleteByRoleid(Integer roleid);
}
