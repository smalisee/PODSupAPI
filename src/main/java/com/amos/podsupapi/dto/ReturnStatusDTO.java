package com.amos.podsupapi.dto;

import java.io.Serializable;

import com.amos.podsupapi.common.ReturnCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ReturnStatusDTO implements Serializable{

	@JsonIgnore
	private static final long serialVersionUID = 1L;

	@JsonProperty("return_code")
	private int returnCode;

	@JsonProperty("return_message")
	private String message;

	@JsonProperty("web_message")
	private String webMessage;

	public ReturnStatusDTO() {
		this(ReturnCode.INTERNAL_WEB_SERVICE_ERROR);
	}

	public ReturnStatusDTO(ReturnCode returnCode) {
		setReturnCode(returnCode.getCode());
		setMessage(returnCode.getMessage());
		setWebMessage(returnCode.getWebMessage());
	}

}
