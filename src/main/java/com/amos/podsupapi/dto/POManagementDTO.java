package com.amos.podsupapi.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class POManagementDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String orderDate;
  private String channel;
  private String orderNo;
  private String payMode;
  private String firstName;
  private String lastName;
  private String phoneNo;
  private String cvCode;
  private String cvName;
  private Integer poNo;
  private Integer productLine;
  private String statusSend;
  private List<ItemDetailDTO> itemNo = new ArrayList<>();
  private String address;
  private String sendDate;
  private String sendBy;
  private String tracking;
  private List<Integer> images = new ArrayList<>();

}
