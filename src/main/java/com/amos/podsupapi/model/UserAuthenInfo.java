package com.amos.podsupapi.model;

import java.util.ArrayList;
import java.util.List;

import com.amos.podsupapi.dto.ReturnStatusDTO;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthenInfo {

  @JsonProperty("tokenId")
  private String tokenId = "";
  @JsonIgnore
  private String group = "";
  @JsonProperty("username")
  private String username = "";
  @JsonIgnore
  private String firstname = "";
  @JsonIgnore
  private String surname = "";

  @JsonProperty("name")
  private String name;

  @JsonProperty("vendor")
  private int vendor;

  @JsonProperty("prodLines")
  private List<ProductLine> prodLines = new ArrayList<>();

  @JsonProperty("phone")
  private String phoneno;

  @JsonProperty("email")
  private String email;

  @JsonProperty("id")
  private Integer id;

  @JsonProperty("type")
  private String type;

  private List<Role> role;

  private ReturnStatusDTO returnResult;

  @JsonGetter("return_result")
  public ReturnStatusDTO getReturnResult() {
    return returnResult;
  }

}
