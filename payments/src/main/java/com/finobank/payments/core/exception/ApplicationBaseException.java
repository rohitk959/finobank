package com.finobank.payments.core.exception;

import lombok.Getter;

@Getter
public class ApplicationBaseException extends RuntimeException {
    public static final String CODE_FAILURE = "FAILURE";
    public static final String CODE_VALIDATION_FAILURE = "VALIDATION_FAILURE";

    private final String code;
    private final String message;
    private final Throwable cause;

    public ApplicationBaseException(final String code, final String message) {
        this(code, message, null);
    }

    public ApplicationBaseException(String code, String message, Throwable cause) {
        this.code = code;
        this.message = message;
        this.cause = cause;
    }
}
