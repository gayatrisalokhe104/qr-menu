package com.qrbased.cafe.exception;

public class PaymentException extends RuntimeException{
	private final int code;
	private final String message;
	public PaymentException(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
}
