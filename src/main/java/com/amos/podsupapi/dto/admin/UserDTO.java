package com.amos.podsupapi.dto.admin;

import java.io.Serializable;
import java.util.List;

import com.amos.podsupapi.dto.admin.RoleDTO;
import com.amos.podsupapi.model.ProductLine;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO implements Serializable {
	
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String username;
	private String password;
	private String type;
	private String passNew;
	
	@JsonProperty("ivendor")
	private int ivendorNo;
	private String status;
	
	private String name;
	
	@JsonProperty("phone")
	private String phoneNo;
	private String email;
	
	@JsonProperty("roles")
	@JsonInclude(Include.NON_NULL)
	private List<RoleDTO> roleList;
	
	@JsonProperty("productlines")
	@JsonInclude(Include.NON_NULL)
	private List<ProductLine> productlines;

}
