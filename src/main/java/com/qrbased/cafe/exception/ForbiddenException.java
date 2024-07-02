package com.qrbased.cafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
	
	private String message;
	private int respondeCode;

    public ForbiddenException(String message, int respondeCode) {
       this.message = message;
       this.respondeCode = respondeCode;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getRespondeCode() {
		return respondeCode;
	}

	public void setRespondeCode(int respondeCode) {
		this.respondeCode = respondeCode;
	}    
}

