package com.finobank.users.core.exception;

import lombok.Getter;

@Getter
public abstract class ApplicationBaseException extends RuntimeException {
    public static final String CODE_FAILURE = "FAILURE";
    
    private final String code;
    private final String message;
    private final Throwable cause;

    ApplicationBaseException(String code, String message) {
        this(code, message, null);
    }

    ApplicationBaseException(String code, String message, Throwable cause) {
        this.code = code;
        this.message = message;
        this.cause = cause;
    }
}
