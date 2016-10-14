package com.acms.dao;

import java.util.List;
import java.util.Map;

import com.acms.entity.Menu;

public interface MenuDao {
    int deleteByPrimaryKey(Integer menuid) throws Exception;

    int insert(Menu record);

    int insertSelective(Menu record) throws Exception;

    Menu selectByPrimaryKey(Integer menuid) throws Exception;

    int updateByPrimaryKeySelective(Menu record) throws Exception;

    int updateByPrimaryKey(Menu record);
    
    List<Menu> selectMenuByParentid(Menu menu) throws Exception;

	List<Menu> selectByPaging(Map query) throws Exception;
	
	int countByPaging(Map<String, Object> query) throws Exception;
	
	int getMenuCount(Integer parentid) throws Exception;
	
	int getMaxSequence(Integer parentid) throws Exception;
	
	int selectMenuCount(Menu menu) throws Exception;
	
	int updateMenuOrder(Menu menu) throws Exception;
	
	int deleteByParentid(Integer parentid) throws Exception;

	int selectMenuid(Menu menu);

	List<Menu> getMenuList(Integer parentid);

	int getMaxLevel(Integer roleid);
}