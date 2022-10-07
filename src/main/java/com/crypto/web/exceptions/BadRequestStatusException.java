package com.crypto.web.exceptions;

public class BadRequestStatusException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadRequestStatusException(String message) {
        super(message);
    }

    public BadRequestStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
