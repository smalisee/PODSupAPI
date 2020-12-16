package com.amos.podsupapi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String keyword;
	private String value;

}
