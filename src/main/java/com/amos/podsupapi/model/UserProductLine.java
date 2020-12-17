package com.amos.podsupapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "W05_P_USER_PRODLINE")
public class UserProductLine implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -7667499295491533889L;

  @Id
  @Column(name = "I_USER_ID")
  @JsonProperty("userId")
  private int userId;

  @Id
  @Column(name = "I_PRODLINE1")
  @JsonProperty("prodline1")
  private int prod1;

  @Id
  @Column(name = "I_PRODLINE3")
  @JsonProperty("prodline3")
  private int prod3;

  @JsonIgnore
  @JsonProperty("mapProd")
  private String mapProd;

}
