package com.amos.podsupapi.common;

public enum UserType {
	INTERNAL("I", "Internal"),
	EXTERNAL("E", "External");

	private final String code;
	private final String message;

	UserType(String code, String message) {
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
