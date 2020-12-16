package com.amos.podsupapi.dto.admin;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutoFactoryPODTO implements Serializable {

  @JsonIgnore
  private static final long serialVersionUID = 1L;

  private int conpany;
  private int order;
  private String cvsOrder;
  private String externOrderNo;
  private int detailPo;
  private int tempPoNo;

  // @JsonProperty("po_no")
  private int pono;

  private int reviseNo;
  private int season;
  private int vender;
  private String status;
  private String create;
  private String poCode;
  private int account;
  private String shipTo;

}
