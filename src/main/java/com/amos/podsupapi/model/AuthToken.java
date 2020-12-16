package com.amos.podsupapi.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "W05_P_AUTH_TOKEN")
public class AuthToken implements Serializable {

  private static final long serialVersionUID = 6231490082697481754L;

  @Id
  @Column(name = "S_USERNAME", updatable = false)
  private String username;

  @Id
  @Column(name = "S_TOKEN", updatable = false)
  private String token;

  @Column(name = "S_DEVICE", updatable = false)
  private String device;

  @Column(name = "D_CREATE", updatable = false)
  private LocalDate createDate;

}
