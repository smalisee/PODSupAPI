package com.amos.podsupapi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "W05_P_FACTORY_POD_STATUS_HIS")
public class PODStatusHistory implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 8930894420213406968L;

  @Id
  @Column(name = "I_PONO")
  private int pono;

  @Temporal(TemporalType.DATE)
  @Column(name = "D_DELIVERY")
  private Date d_delivery;

  @Column(name = "C_STATUS")
  private String status;

  @Column(name = "I_DELIVERY_BY")
  private int delivery_by;

  @Column(name = "I_USER_ID")
  private int userId;

  @Id
  @Column(name = "D_CREATE")
  private LocalDateTime d_create;

  @Column(name = "I_ORDER")
  private int iorder;
}
