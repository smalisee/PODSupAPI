package com.amos.podsupapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "W05_P_USER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -3010027112755145621L;

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
  @Column(name = "S_PHONENO", columnDefinition = "LONGVARBINARY")
  private String phoneNo;

  @Column(name = "S_EMAIL")
  private String email;

  // @JsonProperty("password_new")
  // @Column(name = "")
  // private String passNew;
  /*
   * Relation
   */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
      CascadeType.PERSIST, CascadeType.ALL }, fetch = FetchType.LAZY)
  @JoinTable(name = "W05_P_USER_ROLE", joinColumns = {
      @JoinColumn(name = "I_USER_ID", nullable = false) },
      inverseJoinColumns = {
          @JoinColumn(name = "I_ROLE_ID", nullable = false) })
  private List<Role> roles = new ArrayList<>();

  // Join prodline
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
      CascadeType.PERSIST, CascadeType.ALL }, fetch = FetchType.LAZY)
  @JoinTable(name = "W05_P_USER_PRODLINE", joinColumns = {
      @JoinColumn(name = "I_USER_ID", nullable = false) },
      inverseJoinColumns = {
          @JoinColumn(name = "I_PRODLINE1", nullable = false),
          @JoinColumn(name = "I_PRODLINE3", nullable = false) })
  private List<ProductLine> prodLines = new ArrayList<>();

  // @JsonProperty("usrProdLine")
  // private List<UserProductLine> usrProdLine;

}
