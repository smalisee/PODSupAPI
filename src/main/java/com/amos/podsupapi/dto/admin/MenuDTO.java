package com.amos.podsupapi.dto.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private int id;
	
	@JsonProperty("name_th")
	private String nameTH;
	
	@JsonProperty("name_en")
	private String nameEN;
	
	private String url;
	
	@JsonIgnore
	private String description;
	
	@JsonIgnore
	private String status;
	
	@JsonIgnore
	private int level;
	private int sequence;	
	private List<MenuSubDTO> subMenues = new ArrayList<>();
}
