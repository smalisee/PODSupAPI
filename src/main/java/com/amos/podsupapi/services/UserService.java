package com.amos.podsupapi.services;

import java.util.List;

import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.admin.UserDTO;

public interface UserService {

  public List<UserDTO> getAllUsers(String type, String username, String name, String email, String tel,
      Integer roleId, String status);

  public UserDTO getUserById(int id);

  public UserDTO getUserByUsername(String username);

  public ReturnCode addOrUpdateUser(String payload) throws Exception;

  public ReturnCode updatePassword(String i_user, String old_password, String new_password, String renew_password);
}
