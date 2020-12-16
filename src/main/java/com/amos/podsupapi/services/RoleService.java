package com.amos.podsupapi.services;

import java.util.List;

import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.admin.RoleDTO;
import com.amos.podsupapi.model.Role;

public interface RoleService {
	public List<RoleDTO> getAllRoles(Role filter);
	public RoleDTO getRoleById(int roleId);
	public ReturnCode addOrUpdateRole(String payload) throws Exception;
}
