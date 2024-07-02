package com.qrbased.cafe.exception;

@SuppressWarnings("serial")
public class EmailAlreadyTakenException extends RuntimeException {

    public EmailAlreadyTakenException(String message) {
        super(message);
    }

}

