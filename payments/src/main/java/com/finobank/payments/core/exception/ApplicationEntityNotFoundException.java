package com.finobank.payments.core.exception;

public class ApplicationEntityNotFoundException extends ApplicationBaseException {
    public ApplicationEntityNotFoundException(String resourceId) {
        super(CODE_FAILURE, "Resource with id " + resourceId + " not found");
    }
}
