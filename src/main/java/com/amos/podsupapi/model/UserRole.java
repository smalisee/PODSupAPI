package com.amos.podsupapi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRole implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("role_id")
  private Integer role_id;

  @JsonProperty("role_name")
  private String role_name;
}
