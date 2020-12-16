package com.amos.podsupapi.repository;

import java.util.List;

import com.amos.podsupapi.model.Menu;
import com.amos.podsupapi.model.Role;

public interface MenuRepository {
	List<Menu> getAllMenues();
	List<Menu> getAllMenues(Role role);
	List<Menu> getMenuByPermission(List<Integer> role);
	
	Menu getMenuById(int menuId);
	int getMaxSequence();
    void addMenu(Menu menu);
    void updateMenu(Menu menu);
    void deleteMenu(int menuId);
}
