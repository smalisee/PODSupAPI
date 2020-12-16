package com.amos.podsupapi.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.amos.podsupapi.model.Menu;
import com.amos.podsupapi.model.Role;

@Transactional
@Repository
public class MenuRepositoryImpl implements MenuRepository{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Menu> getAllMenues() {
		String hql = "FROM Menu as menu where menu.parent is null ORDER BY menu.id";
		return entityManager.createQuery(hql, Menu.class).getResultList();
	}

	@Override
	public Menu getMenuById(int menuId) {
		return entityManager.find(Menu.class, menuId);
	}
		
	@Override
	public int getMaxSequence() {
		String hql = "SELECT MAX(sequence) FROM Menu where level = 1";
		int maxSequence = (int) entityManager.createQuery(hql).getSingleResult();
		return maxSequence+1;
	}
	
	@Override
	public void updateMenu(Menu menu) {
		Menu menucl = getMenuById(menu.getId());
		menucl.setParentId(menu.getParentId());
		menucl.setNameTH(menu.getNameTH());
		menucl.setNameEN(menu.getNameEN());
		menucl.setUrl(menu.getUrl());
		menucl.setDescription(menu.getDescription());
		menucl.setStatus(menu.getStatus());
		menucl.setLevel(menu.getLevel());
		menucl.setSequence(menu.getSequence());
		entityManager.flush();
		
		List<Menu> menuTmp = menucl.getSubMenues();
		for(int i=0 ; i<menuTmp.size() ; i++) {
			Menu menu2 = menuTmp.get(i);
			Menu menucl2 = getMenuById(menu2.getId());
			menucl2.setParentId(menu2.getParentId());
			menucl2.setNameTH(menu2.getNameTH());
			menucl2.setNameEN(menu2.getNameEN());
			menucl2.setUrl(menu2.getUrl());
			menucl2.setDescription(menu2.getDescription());
			menucl2.setStatus(menu2.getStatus());
			menucl2.setLevel(menu2.getLevel());
			menucl2.setSequence(menu2.getSequence());
			entityManager.flush();
			
		}
	}
	
	@Override
	public void addMenu(Menu menu) {
		Menu parent = menu;
		parent.setSequence(getMaxSequence());
		entityManager.persist(parent);
		int i = 1;
		for(Menu child : menu.getSubMenues()) {			
			child.setSequence(i++);
			child.setParent(parent);
			entityManager.persist(child);
		}
		entityManager.flush();
	}
	
	@Override
	public void deleteMenu(int roleId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Menu> getAllMenues(Role role) {
		String hql="select distinct m from Menu m join m.roles r where r.id = :id";
		return entityManager.createQuery(hql, Menu.class)
										.setParameter("id",role.getId())
									.getResultList();
	}

	@Override
	public List<Menu> getMenuByPermission(List<Integer> rList) {
		String hql="select distinct m from Menu m inner join m.roles r"
				+ " where m.status='A' and r.id in :rList"
				+ " order by m.sequence asc";
		return entityManager.createQuery(hql, Menu.class).setParameter("rList", rList)
									.getResultList();
	}
}
