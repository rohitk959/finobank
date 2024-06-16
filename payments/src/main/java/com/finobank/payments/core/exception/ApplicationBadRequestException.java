package com.finobank.payments.core.exception;

import lombok.Builder;

public class ApplicationBadRequestException extends ApplicationBaseException {
    @Builder
    public ApplicationBadRequestException(String code, String message) {
        super(code, message);
    }
}
