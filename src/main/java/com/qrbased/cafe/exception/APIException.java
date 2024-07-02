package com.qrbased.cafe.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class APIException extends RuntimeException {
	private HttpStatus status;
	private String message;

	public APIException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public APIException() {
		super();
	}

	public APIException(String message) {
		super(String.format("%s", message));
		this.message = message;
	}

	public APIException(String message, HttpStatus status, String message1) {

		super(message);
		this.status = status;
		this.message = message1;
	}

	public HttpStatus getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}
}

