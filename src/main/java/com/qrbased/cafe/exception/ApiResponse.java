package com.qrbased.cafe.exception;

public class ApiResponse {
	private final int code;
	private final String message;
	public ApiResponse(int code, String message) {
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
