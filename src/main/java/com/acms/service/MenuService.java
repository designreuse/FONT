package com.acms.service;

import java.util.List;
import java.util.Map;

import com.acms.entity.Menu;

/**
 * 菜单service
 * @author 白鑫
 *	2016年7月6日 上午11:57:56
 */
public interface MenuService {

	/**
	 * 根据父菜单id查询菜单
	 * @param menuid
	 * @return
	 */
	List<Menu> selectMenuByParentid(Menu menu) throws Exception;
	
	/**
	 * 分页查询所有一级菜单
	 * @return
	 * @throws Exception
	 */
	List<Menu> selectByPaging(Map query) throws Exception;
	
	/**
	 * 分页查询一级菜单树数
	 * @param query
	 * @return
	 * @throws Exception
	 */
	int countByPaging(Map<String, Object> query) throws Exception;
	
	/**
	 * 查询菜单数
	 * @param parentid
	 * @return
	 * @throws Exception
	 */
	int getMenuCount(Integer parentid) throws Exception;
	
	/**
	 * 获取菜单最大排序数
	 * @return
	 * @throws Exception
	 */
	int getMaxSequence(Integer parentid) throws Exception;
	
	/**
	 * 获取菜单数
	 * @param menuname
	 * @return
	 * @throws Exception
	 */
	int selectMenuCount(Menu menu) throws Exception;
	
	/**
	 * 更新菜单排序
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	int updateMenuOrder(Menu menu) throws Exception;
	
	/**
	 * 增加父菜单
	 * @param record
	 * @return
	 */
    int insertSelective(Menu record) throws Exception;
    
    Menu selectByPrimaryKey(Integer menuid) throws Exception;
    
    int deleteByPrimaryKey(Integer menuid) throws Exception;
    
    /**
     * 按照父菜单ID删除子菜单
     * @param parentid
     * @return
     * @throws Exception
     */
	int deleteByParentid(Integer parentid) throws Exception;
	
    int updateByPrimaryKeySelective(Menu record) throws Exception;

	int selectMenuid(Menu menu);
	
	List<Menu> getMenuList(Integer parentid);

	/**
	 * 删除菜单及其子菜单
	 * @param menuid
	 * @throws Exception
	 */
	void batchDelete(Integer menuid) throws Exception;

	int getMaxLevel(Integer roleid);
	
}
