package com.amos.podsupapi.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amos.podsupapi.common.CommonJWT;
import com.amos.podsupapi.common.CommonUtils;
import com.amos.podsupapi.common.InterfaceTypeEnum;
import com.amos.podsupapi.common.LDAPAuthen;
import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.common.UserType;
import com.amos.podsupapi.dto.ReturnStatusDTO;
import com.amos.podsupapi.model.AuthToken;
import com.amos.podsupapi.model.ProductLine;
import com.amos.podsupapi.model.Role;
import com.amos.podsupapi.model.User;
import com.amos.podsupapi.model.UserAuthenInfo;
import com.amos.podsupapi.repository.AuthTokenRepositoryImpl;
import com.amos.podsupapi.repository.ProductlineRepository;
import com.amos.podsupapi.repository.RoleRepository;
import com.amos.podsupapi.repository.UserRepository;

@Service
public class LoginService {

  @Autowired
  private UserRepository userDao;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private ProductlineRepository prodRepository;

  @Autowired
  private AuthTokenRepositoryImpl tokenDao;

  Logger logger = LogManager.getLogger(LoginService.class);

  public UserAuthenInfo login(String email, String password, String domain) {
    UserAuthenInfo user = new UserAuthenInfo();
    logger.debug(String.format("try log in Email = %s, Password = ######", email));
    ReturnCode code = ReturnCode.INVALID_USER_OR_PASSWORD;
    String token = null;

    try {
      User uResult = userDao.getUserByEmail(email);
      // Check If User Exist and Active
      if (uResult != null && "A".equals(uResult.getStatus())) {

        // check LDAP for login Type.INTERNAL
        if (UserType.INTERNAL.getCode().equals(uResult.getType())) {

          // Check login LDAP
          LDAPAuthen ap = new LDAPAuthen();
          String[] userl = email.split("@");
          String emp_id = ap.checkDataFromLDAP(userl[0], password, domain);

          // String emp_id = ap.checkDataFromLDAP(email, password, domain);

          // if LDAP has data then Login SUCCESS
          if (emp_id != null && !emp_id.isEmpty()) {
            code = ReturnCode.SUCCESS;
          }
        }
        // check password for login Type.EXTERNAL
        else if (UserType.EXTERNAL.getCode().equals(uResult.getType())) {
          try {
            String passEncryt = CommonUtils.passwordEncrypt(password);
            // if password has match then Login SUCCESS
            if (uResult.getPassword().equals(passEncryt)) {
              code = ReturnCode.SUCCESS;
            }
          } catch (Exception ex) {
            logger.error("Password Encrypt error.", ex);
          }
        }
      }

      // if Login SUCCESS generate token
      if (ReturnCode.SUCCESS.equals(code)) {
        // getUser Role for token
        ArrayList<String> roleIdList = new ArrayList<>();
        for (Role role : uResult.getRoles()) {
          roleIdList.add(Integer.toString(role.getId()));
        }

        // ArrayList<Integer> prodIdList = new ArrayList<>();
        List<ProductLine> prodIdList = new ArrayList<>();
        ProductLine prodIdAdd = new ProductLine();
        uResult.setProdlines(prodRepository.getProdlineByUserId(uResult.getId()));
        for (ProductLine prodRepository : uResult.getProdlines()) {

          prodIdAdd = new ProductLine();
          prodIdAdd.setProdline1(prodRepository.getProdline1());
          prodIdAdd.setProdline3(prodRepository.getProdline3());

          prodIdList.add(prodIdAdd);

        }

        if (UserType.EXTERNAL.getCode().equals(uResult.getType())) {
          token = CommonJWT.jwtTokenGenerateNoExpire(uResult.getUsername(), InterfaceTypeEnum.MOBILE.getCode(),
              roleIdList);
          // save NO EXPIRE token to database
          AuthToken authToken = new AuthToken();
          authToken.setUsername(uResult.getUsername());
          authToken.setToken(token);
          authToken.setDevice(InterfaceTypeEnum.MOBILE.getMessage());
          authToken.setCreateDate(LocalDate.now());
          tokenDao.add(authToken);
        } else if (UserType.INTERNAL.getCode().equals(uResult.getType())) {
          token = CommonJWT.JWTTokenGenerateBaseExpire(uResult.getUsername(), InterfaceTypeEnum.WEB.getCode(), roleIdList);
        }
        user.setTokenId(token);
        user.setName((uResult.getName() == null) ? "" : uResult.getName());
        user.setVendor((uResult.getVendorNo() == null) ? 0 : uResult.getVendorNo());
        user.setEmail((uResult.getEmail() == null) ? "" : uResult.getEmail());
        user.setPhoneno((uResult.getPhoneNo() == null) ? "" : uResult.getPhoneNo());
        user.setId(uResult.getId());
        user.setUsername(uResult.getUsername());
        user.setProdLines(prodIdList);
        user.setType(uResult.getType());

        user.setRole(uResult.getRoles());
      }
      user.setReturnResult(new ReturnStatusDTO(code));

      if (code == ReturnCode.SUCCESS) {
        logger.info("Login with " + CommonUtils.toJsonString(uResult));
      }
    } catch (Exception ex) {
      logger.error("Error on login() :" + ex.getMessage(), ex);
    }
    return user;
  }

  public void logout(String username, String token) {
    logger.info("username : " + username + "   |   token:" + token);
    logger.debug("username : " + username + "   |   token:" + token);
    tokenDao.logoutDeleteToken(username, token);
  }

}
