package com.amos.podsupapi.exception;

public class PoBusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	public PoBusinessException () {
		
	}
	
	public PoBusinessException (String message) {
		super(message);
	}
}
