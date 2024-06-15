package com.finobank.users.core.exception;

import java.util.UUID;

public class ApplicationEntityNotFoundException extends ApplicationBaseException {
    public ApplicationEntityNotFoundException(UUID resourceId) {
        super(CODE_FAILURE, "Resource with id " + resourceId + " not found");
    }
}
