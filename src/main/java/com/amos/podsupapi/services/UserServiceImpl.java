package com.amos.podsupapi.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amos.podsupapi.common.CommonUtils;
import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.admin.RoleDTO;
import com.amos.podsupapi.dto.admin.UserDTO;
import com.amos.podsupapi.model.ProductLine;
import com.amos.podsupapi.model.Role;
import com.amos.podsupapi.model.User;
import com.amos.podsupapi.model.UserExternal;
import com.amos.podsupapi.model.UserProductLine;
import com.amos.podsupapi.repository.ProductlineRepository;
import com.amos.podsupapi.repository.RoleRepository;
import com.amos.podsupapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private ProductlineRepository prodRepository;

  @Autowired
  private LdapService ldapService;

  Logger logger = LogManager.getLogger(LoginService.class);

  @Override
  public List<UserDTO> getAllUsers(String type, String username, String name, String email, String tel,
      Integer roleId, String status) {

    User filter = new User();
    filter.setType(type);
    filter.setEmail(email);
    filter.setName(name);
    filter.setPhoneNo(tel);
    filter.setUsername(username);
    filter.setStatus(status);
    if (roleId != null) {
      Role tmpR = roleRepository.getRoleById(roleId);
      if (tmpR != null) {
        filter.getRoles().add(tmpR);
      } else {
        // WRONG ROLE
        return new ArrayList<>();
      }

    }
    List<UserDTO> userList = new ArrayList<>();
    for (User source : userRepository.getAllUsers(filter)) {
      UserDTO target = new UserDTO();
      BeanUtils.copyProperties(source, target);

      userList.add(target);
    }

    return userList;
  }

  @Override
  public UserDTO getUserById(int id) {
    User source = userRepository.getUserById(id);
    UserDTO user = new UserDTO();
    BeanUtils.copyProperties(source, user);

    ArrayList<RoleDTO> tmpRList = new ArrayList<>();

    if (source.getVendorNo() != null) {
      user.setIvendorNo(source.getVendorNo());
    }

    for (Role rSource : source.getRoles()) {
      RoleDTO rDto = new RoleDTO();
      BeanUtils.copyProperties(rSource, rDto);
      tmpRList.add(rDto);
    }
    user.setRoleList(tmpRList);

    List<Object[]> resultProdline = userRepository.findProductLine(source.getId());

    List<UserProductLine> usrProdLineList = new ArrayList<>();

    if (resultProdline != null) {
      for (Object[] rSourcePrdLine : resultProdline) {
        UserProductLine usrPrdLine = new UserProductLine();

        usrPrdLine.setUserId(Integer.valueOf((String) rSourcePrdLine[0]));
        usrPrdLine.setProd1(Integer.valueOf((String) rSourcePrdLine[1]));
        usrPrdLine.setProd3(Integer.valueOf((String) rSourcePrdLine[2]));
        usrPrdLine.setMapProd(usrPrdLine.getProd3() + " - " + usrPrdLine.getProd1());

        usrProdLineList.add(usrPrdLine);
      }
      user.setUsrProdLine(usrProdLineList);
    }

    return user;
  }

  @Override
  public UserDTO getUserByUsername(String username) {
    return getUserById(userRepository.getUserByUsername(username).getId());
  }

  @Override
  public ReturnCode addOrUpdateUser(String payload) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    User user = mapper.readValue(payload, User.class);
    ReturnCode code = ReturnCode.INVALID_USER_OR_PASSWORD;

    List<Role> addRoles = new ArrayList<>();
    for (Role r : user.getRoles()) {
      addRoles.add(roleRepository.getRoleById(r.getId()));
    }
    user.setRoles(addRoles);

    List<ProductLine> addProdlines = new ArrayList<>();
    for (ProductLine p : user.getProdLines()) {
      addProdlines.add(prodRepository.getProdlineById(p.getProdline1(), p.getProdline3()));
    }
    user.setProdLines(addProdlines);

    if (user.getId() == null) {
      if (userRepository.userExists(user.getUsername())) {
        return ReturnCode.USERNAME_ALREADY_EXIST;
      }
      if (!CommonUtils.isNullOrEmpty(user.getPassword())) {
        if (user.getPassword().length() < 4) {
          return ReturnCode.INVALID_PASSWORD_LENGTH;
        } else {
          user.setPassword(CommonUtils.passwordEncrypt(user.getPassword()));
        }
      }
      try {
        userRepository.addUser(user);
        code = ReturnCode.SUCCESS;
      } catch (Exception ex) {
        code = ReturnCode.USERNAME_ALREADY_EXIST;
        logger.error("addUser ERROR :", ex);
      }
    } else {
      // Update Password
      // if(user.getUsername() == null || user.getUsername().isEmpty()) {
      // List<Role> addRolesPass = new ArrayList<>();
      // UserDTO usrcl = getUserById(user.getId());
      // UserDTO userPass = mapper.readValue(payload, UserDTO.class);
      //
      // try {
      // String passEncryt = CommonUtils.passwordEncrypt(user.getPassword());
      // // Validate Password
      // if (usrcl.getPassword().equals(passEncryt)) {
      // user.setPassword(CommonUtils.passwordEncrypt(userPass.getPassNew()));
      // user.setName(usrcl.getName());
      // user.setStatus(usrcl.getStatus());
      // user.setPhoneNo(usrcl.getPhoneNo());
      // user.setVendorNo(usrcl.getIvendorNo());
      // user.setType(usrcl.getType());
      // user.setUsername(usrcl.getUsername());
      // for(RoleDTO rol :usrcl.getRoleList()) {
      // addRolesPass.add(roleRepository.getRoleById(rol.getId()));
      // }
      // user.setRoles(addRolesPass);
      // code = ReturnCode.SUCCESS;
      // userRepository.updateUser(user);
      // }else {
      // code = ReturnCode.INVALID_USER_OR_PASSWORD;
      // }
      // } catch (Exception ex) {
      // logger.error("Password Encrypt error.", ex);
      // }
      //
      // }else {
      User checkType = userRepository.getUserById(user.getId());

      if (checkType.getType().equalsIgnoreCase("I")) {
        if (user.getPassword() == null) {
          user.setPassword(userRepository.getUserById(user.getId()).getPassword());

        } else {
          user.setPassword(CommonUtils.passwordEncrypt(user.getPassword()));
        }
        userRepository.updateUserInternal(user);
        code = ReturnCode.SUCCESS;

      } else if (checkType.getType().equalsIgnoreCase("E")) {
        UserExternal usrEx = mapper.readValue(payload, UserExternal.class);
        if (user.getPassword() == null) {
          usrEx.setPassword(userRepository.getUserById(user.getId()).getPassword());

        } else {
          usrEx.setPassword(CommonUtils.passwordEncrypt(user.getPassword()));
        }
        userRepository.updateUserExternal(usrEx);
        code = ReturnCode.SUCCESS;
      }
      // }
      return code;
    }
    return code;
  }

  @Override
  public ReturnCode updatePassword(String i_user, String old_password, String new_password, String renew_password) {
    // TODO Auto-generated method stub
    // User usr = userRepository.getUserById(Integer.valueOf(i_user));
    try {
      User usr = userRepository.getUserById(Integer.valueOf(i_user));

      String hashPassword_Old = CommonUtils.passwordEncrypt(old_password);

      if ((usr.getPassword()).equalsIgnoreCase(hashPassword_Old)) {
        if (new_password.equalsIgnoreCase(renew_password)) {
          String hashPassword_New = CommonUtils.passwordEncrypt(new_password);

          userRepository.updatePassword(usr, hashPassword_New);
          return ReturnCode.SUCCESS;
        } else {
          return ReturnCode.INVALID_RE_PASSWORD;
        }
      }

    } catch (Exception e) {
      logger.error(String.format("Update Password ERROR."), e);
    }
    return ReturnCode.INVALID_USER_OR_PASSWORD;
  }
}
