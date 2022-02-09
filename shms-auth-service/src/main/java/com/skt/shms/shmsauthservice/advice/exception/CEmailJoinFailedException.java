package com.skt.shms.shmsauthservice.advice.exception;

public class CEmailJoinFailedException extends RuntimeException{
    public CEmailJoinFailedException() {
        super();
    }

    public CEmailJoinFailedException(String message) {
        super(message);
    }

    public CEmailJoinFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
