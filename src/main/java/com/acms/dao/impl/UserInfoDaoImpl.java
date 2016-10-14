package com.acms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.acms.dao.UserInfoDao;
import com.acms.entity.TUserInfo;

@Repository("userInfoDao")
public class UserInfoDaoImpl extends AbstractMyBatisDaoImpl<TUserInfo> implements UserInfoDao{

	public static final String NAMESPACE = "com.acms.dao.UserInfoDao";
	
	@Override
	public List<TUserInfo> selectByPaging(Map<String, Object> query) {
		return getSessionTemplate().selectList(NAMESPACE + ".selectByPaging", query);
	}

	@Override
	public int countByPaging(Map<String, Object> query) {
		return getSessionTemplate().selectOne(NAMESPACE + ".countByPaging", query);
	}

	@Override
	public int getUsernameCount(String username) {
		return getSessionTemplate().selectOne(NAMESPACE + ".getUsernameCount", username);
	}

	@Override
	public int add(TUserInfo user) {
		return getSessionTemplate().insert(NAMESPACE + ".add", user);
	}

	@Override
	public int delete(Integer userid) {
		return getSessionTemplate().delete(NAMESPACE + ".delete", userid);
	}

	@Override
	public int checkOldpwd(TUserInfo user) {
		return getSessionTemplate().selectOne(NAMESPACE + ".checkOldpwd", user);
	}

	@Override
	public int submitNewpwd(TUserInfo user) {
		return getSessionTemplate().update(NAMESPACE + ".submitNewpwd", user);
	}

	@Override
	public List<TUserInfo> selectBySelective(TUserInfo record) {
		// TODO Auto-generated method stub
		return getSessionTemplate().selectList(NAMESPACE + ".selectBySelective", record);
	}

	@Override
	public int deleteByRoleid(Integer roleid) {
		return getSessionTemplate().delete(NAMESPACE + ".deleteByRoleid", roleid);
	}

}
