package com.amos.podsupapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amos.podsupapi.common.CommonUtils;
import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.config.AppConfig;
import com.amos.podsupapi.dto.APIResultDTO;
import com.amos.podsupapi.dto.ReturnStatusDTO;
import com.amos.podsupapi.model.UserAuthenInfo;
import com.amos.podsupapi.services.LoginService;

@RestController
public class LoginController {

  @Autowired
  private LoginService loginService;

  @CrossOrigin
  @RequestMapping(value = "/login", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
  public ResponseEntity<UserAuthenInfo> login(@RequestParam(name = "email", required = true) String email,
      @RequestParam(name = "password", required = true) String password,
      @RequestParam(name = "domain", required = false) String domain) {

    if (CommonUtils.isNullOrEmpty(domain)) {
      domain = AppConfig.getLdapDomain();
    }

    return new ResponseEntity<>(this.loginService.login(email, password, domain), HttpStatus.OK);
  }

  @CrossOrigin
  @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = {
      MediaType.APPLICATION_JSON_UTF8_VALUE })
  public ResponseEntity<APIResultDTO> mobileLogout(@RequestParam(name = "username", required = true) String username,
      @RequestParam(name = "tokenId", required = true) String password) {

    this.loginService.logout(username, password);

    return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS)), HttpStatus.OK);
  }

}
