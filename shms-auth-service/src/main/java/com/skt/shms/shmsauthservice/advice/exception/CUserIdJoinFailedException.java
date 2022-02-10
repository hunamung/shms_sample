package com.skt.shms.shmsauthservice.advice.exception;

public class CUserIdJoinFailedException extends RuntimeException{
    public CUserIdJoinFailedException() {
        super();
    }

    public CUserIdJoinFailedException(String message) {
        super(message);
    }

    public CUserIdJoinFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
