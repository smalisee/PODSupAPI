package com.amos.podsupapi.common;

public enum InterfaceTypeEnum {
	MOBILE("M", "MOBILE"),
	WEB("W", "WEB");

	private final String code;
	private final String message;

	InterfaceTypeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
