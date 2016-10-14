package com.acms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.acms.dao.MenuDao;
import com.acms.dao.RoleMenuDao;
import com.acms.entity.Menu;
import com.acms.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Resource
	MenuDao menuDao;
	@Resource
	RoleMenuDao roleMenuDao;
	
	@Override
	public List<Menu> selectMenuByParentid(Menu menu) throws Exception {
		List<Menu> list = menuDao.selectMenuByParentid(menu);
		if (list !=null && list.size() != 0) {
			for (Menu m : list) {
				menu.setParentid(m.getMenuid());
				m.setChildMenus(selectMenuByParentid(menu));
			}
		}
		return list;
	}

	@Override
	public List<Menu> selectByPaging(Map query) throws Exception {
		return menuDao.selectByPaging(query);
	}

	@Override
	public int countByPaging(Map<String, Object> query) throws Exception {
		return menuDao.countByPaging(query);
	}

	@Override
	public int getMenuCount(Integer parentid) throws Exception {
		return menuDao.getMenuCount(parentid);
	}

	@Override
	public int getMaxSequence(Integer parentid) throws Exception {
		return menuDao.getMaxSequence(parentid);
	}

	@Override
	public int selectMenuCount(Menu menu) throws Exception {
		return menuDao.selectMenuCount(menu);
	}

	@Override
	public int updateMenuOrder(Menu menu) throws Exception {
		return menuDao.updateMenuOrder(menu);
	}

	@Override
	public int insertSelective(Menu record) throws Exception {
		return menuDao.insertSelective(record);
	}

	@Override
	public Menu selectByPrimaryKey(Integer menuid) throws Exception {
		return menuDao.selectByPrimaryKey(menuid);
	}

	@Override
	public int deleteByPrimaryKey(Integer menuid) throws Exception {
		return menuDao.deleteByPrimaryKey(menuid);
	}

	@Override
	public int deleteByParentid(Integer parentid) throws Exception {
		return menuDao.deleteByParentid(parentid);
	}

	@Override
	public int updateByPrimaryKeySelective(Menu record) throws Exception {
		return menuDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int selectMenuid(Menu menu) {
		return menuDao.selectMenuid(menu);
	}

	@Override
	public List<Menu> getMenuList(Integer parentid) {
		return menuDao.getMenuList(parentid);
	}

	@Override
	public void batchDelete(Integer menuid) throws Exception {
		List<Menu> lst = menuDao.getMenuList(menuid);
		if (lst !=null && lst.size() != 0) {
			for (Menu menu : lst) {
				batchDelete(menu.getMenuid());
			}
		}
		menuDao.deleteByPrimaryKey(menuid);
		roleMenuDao.deleteByMenuid(menuid);
		
	}

	@Override
	public int getMaxLevel(Integer roleid) {
		return menuDao.getMaxLevel(roleid);
	}
}
