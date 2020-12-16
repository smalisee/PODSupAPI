package com.amos.podsupapi.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class POManagentViewDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  private String orderDate;
  private String channel;
  private String orderNo;
  private String payMode;
  private String firstNameCus;
  private String lastNameCus;
  private String phoneNoCus;
  private List<ItemDetailDTO> itemList = new ArrayList<>();
  private String firstNameShip;
  private String lastNameShip;
  private String phoneNoShip;
  private String address1;
  private String address7;
  private String address2;
  private String address3;
  private String city;
  private String zipcode;
  private String statusSend;
  private String sendDate;
  private String sendBy;
  private String i_order;
  private String tracking;
  private String preview;
  private List<Integer> file = new ArrayList<>();

}
