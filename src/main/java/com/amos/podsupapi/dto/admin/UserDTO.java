package com.amos.podsupapi.dto.admin;

import java.io.Serializable;
import java.util.List;

import com.amos.podsupapi.model.ProductLine;
import com.amos.podsupapi.model.UserProductLine;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -7699460429731905334L;
  private int id;
  private String username;
  private String password;
  private String type;
  private String passNew;

  @JsonProperty("ivendor")
  private int ivendorNo;
  private String status;

  private String name;

  @JsonProperty("phone")
  private String phoneNo;
  private String email;

  @JsonProperty("roles")
  private List<RoleDTO> roleList;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("productlines")
  private List<ProductLine> productlines;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("usrProdLine")
  private List<UserProductLine> usrProdLine;

}
