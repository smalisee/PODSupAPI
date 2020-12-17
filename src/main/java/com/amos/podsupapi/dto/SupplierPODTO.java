package com.amos.podsupapi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierPODTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String iserial;
  private String orderNo;
  private String firstname;
  private String lastname;
  private String poNo;
  private String delivery_status;
  private String delivery_date;
  private String delivery_by;
  private String trackingNo;
  private String remark;

}
