package com.amos.podsupapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "P_AUTO_FACTORY_POLINK", schema = "NATLNX", catalog = "NATLNX")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutoFactoryPO implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "I_COMPANY")
  private Integer commany;

  @Id
  @Column(name = "I_ORDER")
  private Integer order;

  @Column(name = "D_CVS_ORDER")
  private String cvsOrder;

  @Column(name = "S_EXTERN_ORDERNO")
  private String externOrderNo;

  @Column(name = "I_DETAIL_PO")
  private Integer detailPO;

  @Column(name = "I_TEMPPONO")
  private Integer tempPONo;

  @Id
  // @JsonProperty("pono")
  @Column(name = "I_PONO")
  private int pono;

  @Column(name = "I_REVISENO")
  private Integer reviseNo;

  @Column(name = "I_SEASON")
  private Integer season;

  @Column(name = "I_VENDOR")
  private Integer vender;

  @Column(name = "C_STATUS")
  private String status;

  @Column(name = "D_CREATE")
  private String create;

  @Column(name = "S_OPCODE")
  private String opCode;

  @Column(name = "I_ACCOUNT")
  private Integer account;

  @Column(name = "C_SHIPTO")
  private String shipTo;

  @Column(name = "S_PREVIEW")
  private String preview;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "i_order", insertable = false, updatable = false)
  @Fetch(FetchMode.JOIN)
  private ShipTo shipto;

  // @ManyToMany(mappedBy = "autoPO")
  // private List<ShipTo> shipto = new ArrayList<>();
  //
}
