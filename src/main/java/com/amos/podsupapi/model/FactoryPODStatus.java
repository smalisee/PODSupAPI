package com.amos.podsupapi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "W05_P_FACTORY_POD_STATUS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FactoryPODStatus implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -9036029058309703973L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "W05_P_FACTORY_POD_STATUS_SEQ")
  @SequenceGenerator(name = "W05_P_FACTORY_POD_STATUS_SEQ", sequenceName = "W05_P_FACTORY_POD_STATUS_SEQ", allocationSize = 1,
      initialValue = 1)
  @Column(name = "I_SERIAL")
  private Integer id;

  @Column(name = "I_PONO")
  private int pono;

  @Column(name = "D_CHANGE")
  private LocalDateTime d_change;

  @Temporal(TemporalType.DATE)
  @Column(name = "D_DELIVERY")
  private Date d_delivery;

  @Column(name = "C_STATUS")
  private String status;

  @Column(name = "I_DELIVERY_BY")
  private int delivery_by;

  @Column(name = "S_DELIVERY_OTHER")
  private String delivery_other;

  @Column(name = "S_DELIVERY_TRACKING")
  private String tracking;

  @Column(name = "I_USER_ID")
  private int userId;

  @Column(name = "I_ORDER")
  private int orderNo;

}
