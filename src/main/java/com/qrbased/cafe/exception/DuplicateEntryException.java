package com.qrbased.cafe.exception;

public class DuplicateEntryException extends RuntimeException{

	public DuplicateEntryException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateEntryException(String message) {
		super(message);
	}

}
