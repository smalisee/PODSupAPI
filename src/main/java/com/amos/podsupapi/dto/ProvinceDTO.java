package com.amos.podsupapi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvinceDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String province;
  private String district;
  private String subDistrict;
  private String zipcode;

}
