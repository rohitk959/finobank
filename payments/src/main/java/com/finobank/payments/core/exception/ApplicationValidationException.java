package com.finobank.payments.core.exception;

import java.util.List;

public class ApplicationValidationException extends ApplicationBaseException {
    public ApplicationValidationException(List<String> errorMessages) {
        super(CODE_VALIDATION_FAILURE, String.join(", ", errorMessages));
    }
}
