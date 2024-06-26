package com.finobank.payments.core.service;

import com.finobank.payments.core.model.ApiPayment;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    List<ApiPayment> getPayments(String accountNumber);

    ApiPayment makePayment(ApiPayment payment);

    void deletePaymentById(UUID paymentId);
}
