package com.amos.podsupapi.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResultDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("return_result")
  private ReturnStatusDTO returnStatus;

  @JsonProperty("data")
  private Serializable data;

  @JsonProperty("view")
  private String view;

  @JsonProperty("search")
  private Serializable search;

  public APIResultDTO(ReturnStatusDTO returnStatus) {
    setReturnStatus(returnStatus);
  }

  public APIResultDTO(ReturnStatusDTO returnStatus, Object data) {
    setReturnStatus(returnStatus);
    setData((Serializable) data);
  }
}
