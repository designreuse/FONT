package com.acms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acms.controller.AbstractController;
import com.acms.dao.UserInfoDao;
import com.acms.entity.TUserInfo;
import com.acms.service.UserInfoService;
import com.acms.util.ValidateUtils;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
	
	@Resource
	private UserInfoDao userDao;

	@Override
	public TUserInfo getEntity(String key, TUserInfo entity) throws Exception{
		return userDao.getEntity(key, entity);
	}

	@Override
	@Transactional
	public void updateEntity(String key, TUserInfo entity) throws Exception {
		userDao.updateEntity(key, entity);
	}

	@Override
	@Transactional
	public void updateEntity(TUserInfo query) throws Exception {
		userDao.updateEntity("com.acms.dao.UserInfoDao.update", query);
	}

	@Override
	public TUserInfo login(TUserInfo userLogin) throws Exception {
		TUserInfo dbUser = userDao.getEntity("com.acms.dao.UserInfoDao.login", userLogin);
		if (null != dbUser) {
			// 记录登录信息
			dbUser.setLastloginip(dbUser.getLoginip());
			dbUser.setLoginip(userLogin.getLoginip());
			
			dbUser.setLastlogintime(dbUser.getLogintime());
			dbUser.setLogintime(AbstractController.getNow());
			
			dbUser.setLogintimes(dbUser.getLogintimes() + 1);
			
			userDao.updateEntity("com.acms.dao.UserInfoDao.update", dbUser);
			return dbUser;
		}
		return null;
	}

	@Override
	public List<TUserInfo> selectList(Map map) throws Exception {
		return userDao.selectListByMap("com.acms.dao.UserInfoDao.selectListByMap", map);
	}

	@Override
	public List<TUserInfo> selectList(String string, TUserInfo query) throws Exception {
		return userDao.selectList(string, query);
	}

	@Override
	public List<TUserInfo> selectByPaging(Map<String, Object> query) {
		return userDao.selectByPaging(query);
	}

	@Override
	public int countByPaging(Map<String, Object> query) {
		return userDao.countByPaging(query);
	}

	@Override
	public int getUsernameCount(String username) {
		return userDao.getUsernameCount(username);
	}

	@Override
	public int add(TUserInfo user) {
		return userDao.add(user);
	}

	@Override
	public int delete(Integer userid) {
		return userDao.delete(userid);
	}

	@Override
	public int checkOldpwd(TUserInfo user) {
		return userDao.checkOldpwd(user);
	}

	@Override
	public int submitNewpwd(TUserInfo user) {
		return userDao.submitNewpwd(user);
	}

	@Override
	public List<TUserInfo> selectBySelective(TUserInfo record) {
		// TODO Auto-generated method stub
		return userDao.selectBySelective(record);
	}

	@Override
	public int deleteByRoleid(Integer roleid) {
		return userDao.deleteByRoleid(roleid);
	}

	@Override
	public long saveEntity(String key, TUserInfo obj) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteEntity(String key, TUserInfo obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUserCenter(TUserInfo query) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
