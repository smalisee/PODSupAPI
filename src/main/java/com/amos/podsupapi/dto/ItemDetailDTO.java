package com.amos.podsupapi.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDetailDTO implements Serializable {

	private static final long serialVersionUID = 1L;
    private Integer itemNo;
    private String itemName;
    private Integer qty;
    private Double totalPrice;
	
}
