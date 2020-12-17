package com.amos.podsupapi.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "P_SHIPTO", schema = "LNX", catalog = "LNX")
// @IdClass(ShipToPK.class)
// @SecondaryTable(name="P_AUTO_FACTORY_POLINK", schema = "NATLNX",catalog = "NATLNX")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipTo implements Serializable {

  private static final long serialVersionUID = 1L;

  // @Id
  @Column(name = "I_ACCOUNT")
  private Integer account;
  //
  // @Id
  @Column(name = "I_COMPANY")
  private Integer company;
  //
  // @Id
  @Column(name = "I_RUNNING")
  private Integer running;

  @Id
  @Column(name = "I_ORDER")
  private Integer order;

  @Column(name = "C_SHIPTO")
  private String shipTo;

  @Column(name = "S_CUSTID")
  private String custId;

  @Column(name = "S_TITLE")
  private String title;

  @Column(name = "S_NAME")
  private String name;

  @Column(name = "S_FIRSTNAME")
  private String firstName;

  @Column(name = "S_CITY")
  private String city;

  @Column(name = "S_ADDRESS1")
  private String address1;

  @Column(name = "S_ADDRESS2")
  private String address2;

  @Column(name = "S_ADDRESS3")
  private String address3;

  @Column(name = "I_ZIPCODE")
  private Integer zipCode;

  @Column(name = "C_SEX")
  private String sex;

  @Column(name = "S_PHONENO1")
  private String phoneNo1;

  @Column(name = "S_PHONENO2")
  private String phoneNo2;

  @Column(name = "S_PHONENO3")
  private String phoneNo3;

  // @Temporal(TemporalType.DATE)
  @Column(name = "D_CREATE")
  private LocalDate create;

  // @Temporal(TemporalType.DATE)
  @Column(name = "D_CHANGE")
  private LocalDate change;

  @Column(name = "S_OPCODE")
  private String opCode;

  @Column(name = "S_FAXNO")
  private String faxNo;

  @Column(name = "S_EMAIL")
  private String email;

  @Column(name = "I_COUNTRY")
  private Integer country;

  @Column(name = "S_ADDRESS7")
  private String address7;

  @Temporal(TemporalType.DATE)
  @Column(name = "D_FROZEN")
  private Date frozen;

  @Temporal(TemporalType.DATE)
  @Column(name = "D_REMOVED")
  private Date remove;

  @OneToMany(targetEntity = AutoFactoryPO.class, mappedBy = "order", orphanRemoval = false, fetch = FetchType.LAZY)
  private Set<AutoFactoryPO> autoPO;

  // @ManyToMany(cascade = { CascadeType.DETACH,
  // CascadeType.MERGE,
  // CascadeType.REFRESH,
  // CascadeType.PERSIST},
  // fetch = FetchType.LAZY)
  // @JoinTable(
  // name = "P_AUTO_FACTORY_POLINK",schema = "NATLNX",catalog = "NATLNX",
  // joinColumns = { @JoinColumn(name="I_ORDER",nullable = false) },
  // inverseJoinColumns = { @JoinColumn(name = "I_ORDER",nullable = false) }
  // )
  // private List<AutoFactoryPO> autoPO = new ArrayList<>();

}
