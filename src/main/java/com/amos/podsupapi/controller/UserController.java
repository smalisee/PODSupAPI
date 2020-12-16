package com.amos.podsupapi.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.APIResultDTO;
import com.amos.podsupapi.dto.ReturnStatusDTO;
import com.amos.podsupapi.dto.admin.UserDTO;
import com.amos.podsupapi.services.LdapService;
import com.amos.podsupapi.services.UserService;
import com.fasterxml.jackson.core.JsonParseException;

@Controller
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private LdapService ldapService;

  private static Logger logger = LogManager.getLogger(UserController.class);

  @CrossOrigin
  @GetMapping(value = "/get_all_users", produces = { "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> getAllUsers(@RequestParam(name = "type", required = false) String type,
      @RequestParam(name = "username", required = false) String username,
      @RequestParam(name = "roleId", required = false) Integer roleId,
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "email", required = false) String email,
      @RequestParam(name = "tel", required = false) String tel,
      @RequestParam(name = "status", required = false) String status) {
    List<UserDTO> result = userService.getAllUsers(type, username, name, email, tel, roleId, status);
    return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), result), HttpStatus.OK);

  }

  @CrossOrigin
  @RequestMapping(value = "/update_user", consumes = { "application/json" }, produces = {
      "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> addOrUpdateUser(@RequestBody String payload) {

    ReturnCode code = ReturnCode.SUCCESS;

    try {
      code = userService.addOrUpdateUser(payload);
    } catch (JsonParseException ex) {
      code = ReturnCode.INVALID_REQUEST_PARAM;
      logger.error(String.format("Add User ERROR. payload: %s", payload), ex);
    } catch (Exception ex) {
      code = ReturnCode.INTERNAL_WEB_SERVICE_ERROR;
      logger.error(String.format("Add User ERROR. payload: %s", payload), ex);
      System.out.println(ex);
    }

    return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(code)), HttpStatus.OK);

  }

  @CrossOrigin
  @RequestMapping(value = "/get_user_by_id", produces = { "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> getUserById(@RequestParam(name = "id", required = true) int id) {
    UserDTO result = userService.getUserById(id);
    return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), result), HttpStatus.OK);
  }

  @CrossOrigin
  @GetMapping(value = "/get_user_external_info", produces = { "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> getUserByName(@RequestParam(name = "username", required = true) String username) {
    UserDTO result = userService.getUserByUsername(username);
    return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), result), HttpStatus.OK);
  }

  @CrossOrigin
  @GetMapping(value = "/get_user_ldap", produces = { "application/json; charset=UTF-8" })
  public ResponseEntity<String> getUserInfoByLdap(@RequestParam(name = "username", required = true) String username) {

    String dataJson = "";
    try {
      dataJson = ldapService.getUserInfo(username);

    } catch (Exception e) {
      logger.error(String.format("Get user info ERROR. username: %s", username), e);
    }

    return new ResponseEntity<>(dataJson, HttpStatus.OK);
  }

  @CrossOrigin
  @PostMapping(value = "/update_password", produces = { "application/json; charset=UTF-8" })
  public ResponseEntity<APIResultDTO> changePassword(@RequestParam(name = "i_user", required = false) String i_user,
      @RequestParam(name = "old_password", required = false) String old_password,
      @RequestParam(name = "new_password", required = false) String new_password,
      @RequestParam(name = "renew_password", required = false) String renew_password) {

    ReturnCode result = userService.updatePassword(i_user, old_password, new_password, renew_password);
    return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(result)), HttpStatus.OK);
  }

}
