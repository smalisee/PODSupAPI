package com.amos.podsupapi.services;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.admin.RoleDTO;
import com.amos.podsupapi.model.Menu;
import com.amos.podsupapi.model.Role;
import com.amos.podsupapi.repository.MenuRepository;
import com.amos.podsupapi.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class RoleServiceImpl implements RoleService {

	@Autowired 
	private RoleRepository roleRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Override
	public List<RoleDTO> getAllRoles(Role filter) {
		List<RoleDTO> dto = new ArrayList<>();
		for (Role source: roleRepository.getAllRoles(filter) ) {
			RoleDTO target= new RoleDTO();
	        BeanUtils.copyProperties(source , target);
	        dto.add(target);
	     }	
		return dto;
	}

	@Override
	public RoleDTO getRoleById(int roleId) {
		RoleDTO target= new RoleDTO();
        BeanUtils.copyProperties(roleRepository.getRoleById(roleId) , target);
		return target;
	}

	@Override
	public ReturnCode addOrUpdateRole(String payload) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Role role = mapper.readValue(payload, Role.class);
		role.setName(role.getName().toUpperCase());
		List<Menu> addMenu = new ArrayList<>(); 
		for(Menu m :role.getMenus()) {				
			addMenu.add(menuRepository.getMenuById(m.getId()));
		}
		role.setMenus(addMenu);	
		
		if(role.getId()==null) {
			if(roleRepository.roleExists(role.getName())) {
				return ReturnCode.DUPLICATE_VALUE;
			}
			roleRepository.addRole(role);
		}else {				
			roleRepository.updateRole(role);
		}
		return ReturnCode.SUCCESS;
	}

}
