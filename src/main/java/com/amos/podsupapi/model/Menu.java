package com.amos.podsupapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="W05_P_MENU")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "W05_P_MENU_SEQ")
	@SequenceGenerator(name = "W05_P_MENU_SEQ", sequenceName = "W05_P_MENU_SEQ", allocationSize = 1, initialValue = 1)
//	@JsonProperty("id")
	@Column(name="I_MENU_ID")
	private int id;

	@JsonProperty("name_th")
	@Column(name="S_NAME_TH")
	private String nameTH;

	@JsonProperty("name_en")
	@Column(name="S_NAME_EN")
	private String nameEN;

	@Column(name="S_URL")
	private String url;

	@Column(name="S_DESCRIPTION")
	private String description;

	@Column(name="C_STATUS")
	private String status;

	@Column(name="I_LEVEL")
	private int level;

	@Column(name="I_SEQUENCE")
	private int sequence;

	@Transient
	private int parentId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional=true)
	@JoinColumn(name="I_PARENT_ID")
	private Menu parent;

	@JsonIgnore
	@OneToMany(mappedBy="parent")
	private List<Menu> subMenues = new ArrayList<>();
	
//	@JsonIgnore
//	@ManyToMany(cascade = { CascadeType.DETACH, 
//	CascadeType.MERGE,
//	CascadeType.REFRESH,
//	CascadeType.PERSIST},
//	fetch = FetchType.LAZY)
//	@JoinTable(
//	name = "W05_P_ROLE_MENU", 
//	joinColumns = { @JoinColumn(name="I_MENU_ID",nullable = false) }, 
//	inverseJoinColumns = { @JoinColumn(name = "I_ROLE_ID",nullable = false) }
//	)
//	private List<Role> roles = new ArrayList<>();
	
	@JsonIgnore
	@ManyToMany(mappedBy = "menus")
	@OrderBy("sequence asc")
	private List<Role> roles = new ArrayList<>();
	
}
