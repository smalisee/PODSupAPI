package com.amos.podsupapi.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class POManageReportSearchDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("dateFrom")
  private String dateFrom;

  @JsonProperty("dateTo")
  private String dateTo;

  @JsonProperty("orderNo")
  private String orderNo;

  @JsonProperty("phoneNo")
  private String phoneNo;

  @JsonProperty("cvCode")
  private String cvCode;

  @JsonProperty("channel")
  private String channel;

  @JsonProperty("status")
  private String status;

  @JsonProperty("poNo")
  private String poNo;

  @JsonProperty("vendor")
  private String vendor;

  @Override
  @JsonIgnore
  public String toString() {
    String str = " ";
    if (this.dateFrom != null) {
      str += String.format(" dateFrom = %s,", this.dateFrom);
    }
    if (this.dateTo != null) {
      str += String.format(" dateTo = %s,", this.dateTo);
    }
    if (this.orderNo != null) {
      str += String.format(" orderNo = %s,", this.orderNo);
    }
    if (this.phoneNo != null) {
      str += String.format(" phoneNo = %s,", this.phoneNo);
    }
    if (this.cvCode != null) {
      str += String.format(" cvCode = %s,", this.cvCode);
    }
    if (this.channel != null) {
      str += String.format(" channel = %s,", this.channel);
    }
    if (this.status != null) {
      str += String.format(" status = %s,", this.status);
    }
    if (this.poNo != null) {
      str += String.format(" poNo = %s,", this.poNo);
    }
    if (this.vendor != null) {
      str += String.format(" vendor = %s,", this.vendor);
    }
    return String.format("( %s  )", str.substring(0, str.length() - 1));

  }

}
