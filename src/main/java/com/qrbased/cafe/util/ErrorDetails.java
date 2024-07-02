package com.qrbased.cafe.util;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorDetails {

	private String message;
	private String details;


	public ErrorDetails( String message, String details) {
		this.message = message;
		this.details = details;
	}


	public void setMessage(String message) {
		this.message = message;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

}

