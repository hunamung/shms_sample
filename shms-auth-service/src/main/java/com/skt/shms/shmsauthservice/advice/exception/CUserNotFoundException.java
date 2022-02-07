package com.skt.shms.shmsauthservice.advice.exception;

import com.skt.shms.shmsauthservice.domain.user.User;

public class CUserNotFoundException extends RuntimeException {

    public CUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CUserNotFoundException(String message) {
        super(message);
    }

    public CUserNotFoundException() {
        super();
    }
}
