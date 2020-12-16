package com.amos.podsupapi.repository;

import java.util.List;

import com.amos.podsupapi.model.User;

public interface UserRepository {

  List<User> getAllUsers();

  List<User> getAllUsers(User filter);

  // List<Role> getAllRoles(int userId);
  //
  User getUserById(int userId);

  void addUser(User user);

  void updateUser(User user);

  // void deleteUser(User user);
  //
  User getUserByEmail(String email);

  boolean userExists(String username);

  User getUserByUsername(String username);

  void updatePassword(User usr, String hashPassword_New);

}
