package com.finobank.payments.core.controller;

import com.finobank.payments.core.api.PaymentsApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentsController implements PaymentsApi {

    @Override
    public ResponseEntity<String> getPayments() {
        return PaymentsApi.super.getPayments();
    }
}
