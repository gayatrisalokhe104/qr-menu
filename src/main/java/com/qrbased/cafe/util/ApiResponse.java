package com.qrbased.cafe.util;

public class ApiResponse<T>{
    private boolean success;
    private String message;
	private String status;
	private int responseCode;
	private T data;
	
	public ApiResponse(boolean success, String message, String status, int responseCode, T data) {
		super();
		this.success = success;
		this.message = message;
		this.status = status;
		this.responseCode = responseCode;
		this.data = data;
	}
	
	public ApiResponse(String message, String status) {
		super();
		this.message = message;
		this.status = status;
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}


