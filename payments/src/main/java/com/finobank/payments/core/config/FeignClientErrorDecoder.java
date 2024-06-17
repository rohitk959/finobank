package com.finobank.payments.core.config;

import com.finobank.payments.core.exception.ApplicationBadRequestException;
import com.finobank.payments.core.exception.ApplicationBaseException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;

public class FeignClientErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    private static ApplicationBaseException readMessage(Response response) {
        try (InputStream is = response.body().asInputStream()) {
            return new ApplicationBadRequestException(ApplicationBaseException.CODE_FAILURE, new String(is.readAllBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return readMessage(response);
        }

        if (response.status() == HttpStatus.BAD_REQUEST.value()) {
            return readMessage(response);
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }
}
