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

//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "W05_P_Role")
public class Role implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "W05_P_ROLE_SEQ")
  @SequenceGenerator(name = "W05_P_ROLE_SEQ", sequenceName = "W05_P_ROLE_SEQ", allocationSize = 1, initialValue = 1)
  @Column(name = "I_ROLE_ID")
  private Integer id;

  // @NaturalId(mutable=true)
  @Column(name = "S_ROLE_NAME")
  private String name;

  @Column(name = "C_STATUS")
  private String status;

  @JsonIgnore
  @ManyToMany(mappedBy = "roles")
  private List<User> users = new ArrayList<>();

  // @JsonIgnore
  // @ManyToMany(mappedBy = "roles")
  // private List<Menu> menus = new ArrayList<>();

  @ManyToMany(cascade = { CascadeType.DETACH,
      CascadeType.MERGE,
      CascadeType.REFRESH,
      CascadeType.PERSIST },
      fetch = FetchType.LAZY)
  @JoinTable(
      name = "W05_P_ROLE_MENU",
      joinColumns = { @JoinColumn(name = "I_ROLE_ID", nullable = false) },
      inverseJoinColumns = { @JoinColumn(name = "I_MENU_ID", nullable = false) })
  private List<Menu> menus = new ArrayList<>();

}
