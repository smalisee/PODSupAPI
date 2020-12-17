package com.amos.podsupapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "W05_P_USER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserExternal implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -7819915061126974977L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "W05_P_USER_SEQ")
  @SequenceGenerator(name = "W05_P_USER_SEQ", sequenceName = "W05_P_USER_SEQ", allocationSize = 1, initialValue = 1)
  @Column(name = "I_USER_ID")
  private Integer id;

  @NaturalId
  @Column(name = "S_USERNAME")
  private String username;

  @Column(name = "S_PASSWORD")
  private String password;

  @Column(name = "C_TYPE")
  private String type;

  @JsonProperty("vendor_no")
  @Column(name = "I_VENDOR")
  private Integer vendorNo;

  @Column(name = "C_STATUS")
  private String status;

  @Column(name = "S_NAME")
  private String name;

  @JsonProperty("phone_no")
  @Column(name = "S_PHONENO")
  private String phoneNo;

  @Column(name = "S_EMAIL")
  private String email;

}
