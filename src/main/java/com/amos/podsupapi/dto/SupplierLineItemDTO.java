package com.amos.podsupapi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierLineItemDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 7204443394790579629L;

  private String itemNo;
  private String itemName;
  private String itemQty;
}
