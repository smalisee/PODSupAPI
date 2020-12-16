package com.amos.podsupapi.repository;

import java.util.List;

import com.amos.podsupapi.model.Role;
import com.amos.podsupapi.model.User;

public interface RoleRepository {
  List<Role> getAllRoles();

  List<Role> getAllRoles(Role filter);

  List<User> getAllUsersByRole(int Role);

  Role getRoleById(int roleId);

  void addRole(Role role);

  void updateRole(Role role);

  void deleteRole(Role roleId);

  boolean roleExists(String rolename);

}
