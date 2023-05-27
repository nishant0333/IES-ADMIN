package com.ies.admin.exception;

import lombok.Builder;


public class ResourceNotFountException extends RuntimeException {

	public ResourceNotFountException() {
		super("Resource not found");

	}

	public ResourceNotFountException(String message) {

		super(message);

	}
}
