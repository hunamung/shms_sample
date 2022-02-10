package com.skt.shms.shmsauthservice.advice.exception;

public class CUserIdLoginFailedException extends RuntimeException {
    public CUserIdLoginFailedException() {
        super();
    }

    public CUserIdLoginFailedException(String message) {
        super(message);
    }

    public CUserIdLoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
