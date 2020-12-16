package com.amos.podsupapi.viewmodel;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyViewModel implements Serializable{
	
	private static final long serialVersionUID = 1L; 
	
	private String item;
	private String value;
	private String name;
	private Integer sorting;
}
