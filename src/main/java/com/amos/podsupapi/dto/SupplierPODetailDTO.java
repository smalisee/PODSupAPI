package com.amos.podsupapi.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierPODetailDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String iserial;
  private String orderNo;
  private String firstname;
  private String lastname;
  private String poNo;
  private String delivery_status_keyword;
  private String delivery_status_value;
  private String delivery_by_keyword;
  private String delivery_by_value;
  private String delivery_date;
  private String tracking_no;

  private List<SupplierLineItemDTO> itemList;
}
