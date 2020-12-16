package com.amos.podsupapi.dto.admin;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuSubDTO implements Serializable {

private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String nameTH;
	private String nameEN;
	private String url;
	
	@JsonInclude(Include.NON_NULL)
	private String parent;
	
	@JsonIgnore
	private String description;
	
	@JsonIgnore
	private String status;
	
	@JsonIgnore
	private int level;
	
	@JsonIgnore
	private int sequence;
}
