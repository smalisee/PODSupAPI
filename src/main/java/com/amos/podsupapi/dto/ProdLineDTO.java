package com.amos.podsupapi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdLineDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer prodline1;
  private Integer prodline3;
  private String mapProd;

}
