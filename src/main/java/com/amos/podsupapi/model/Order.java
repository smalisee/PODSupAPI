package com.amos.podsupapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "P_ORDSUM", schema = "LNX")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "I_ORDER")
  private int order;

  @Column(name = "C_SHIPTO")
  private String shipto;

}
