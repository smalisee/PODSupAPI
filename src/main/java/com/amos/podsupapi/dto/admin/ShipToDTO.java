package com.amos.podsupapi.dto.admin;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipToDTO implements Serializable {

  @JsonIgnore
  private static final long serialVersionUID = 1L;

  // private String orderno;
  private String firstName;
  private String name;
  private String address1;
  private String address7;
  private String address2;
  private String address3;
  private String city;
  private int zipCode;
  private String phoneNo1;
}
