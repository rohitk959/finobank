package com.finobank.accounts.core.config;

import com.finobank.accounts.core.exception.ApplicationBadRequestException;
import com.finobank.accounts.core.exception.ApplicationEntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationEntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String applicationEntityNotFoundException(ApplicationEntityNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ApplicationBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String applicationBadRequestException(ApplicationBadRequestException e) {
        return e.getMessage();
    }

}
