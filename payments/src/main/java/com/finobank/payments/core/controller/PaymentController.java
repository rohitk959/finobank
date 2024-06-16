package com.finobank.payments.core.controller;

import com.finobank.payments.core.api.PaymentsApi;
import com.finobank.payments.core.model.ApiPayment;
import com.finobank.payments.core.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class PaymentController implements PaymentsApi {
    private final PaymentService paymentService;

    @Override
    public ResponseEntity<List<ApiPayment>> getPayments(String accountNumber) {
        List<ApiPayment> payments = paymentService.getPayments(accountNumber);
        return ResponseEntity.ok(payments);
    }

    @Override
    public ResponseEntity<ApiPayment> makePayment(ApiPayment apiPayment) {
        ApiPayment payment = paymentService.makePayment(apiPayment);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }
}
