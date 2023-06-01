package com.ies.admin.exception;




public class ResourceNotFountException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFountException() {
		super("Resource not found");

	}

	public ResourceNotFountException(String message) {

		super(message);

	}
}
