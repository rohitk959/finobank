package com.finobank.payments.core.factory;

import com.finobank.payments.core.domain.Payment;
import com.finobank.payments.core.domain.PaymentStatus;
import com.finobank.payments.core.model.ApiPayment;
import com.finobank.payments.core.model.ApiPaymentStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentFactory {
    public static ApiPayment api(Payment payment) {
        return ApiPayment.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .beneficiaryAccountNumber(payment.getBeneficiaryAccountNumber())
                .communication(payment.getCommunication())
                .creationDate(toOffsetDateTime(payment.getCreationDate()))
                .currency(payment.getCurrency())
                .fraudulentTransaction(payment.getFraudulentTransaction())
                .giverAccountNumber(payment.getGiverAccountNumber())
                .status(getStatus(payment.getStatus()))
                .build();
    }

    private static ApiPaymentStatus getStatus(PaymentStatus status) {
        if (status == null) {
            return null;
        }
        return ApiPaymentStatus.fromValue(status.getValue());
    }

    private static OffsetDateTime toOffsetDateTime(LocalDateTime creationDate) {
        return creationDate.atOffset(ZoneOffset.UTC);
    }

    public static Payment core(ApiPayment payment) {
        return Payment.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .beneficiaryAccountNumber(payment.getBeneficiaryAccountNumber())
                .communication(payment.getCommunication())
                .creationDate(toLocalDateTime(payment.getCreationDate()))
                .currency(payment.getCurrency())
                .fraudulentTransaction(payment.getFraudulentTransaction())
                .giverAccountNumber(payment.getGiverAccountNumber())
                .status(getStatus(payment.getStatus()))
                .build();
    }

    private static PaymentStatus getStatus(ApiPaymentStatus status) {
        if (status == null) {
            return null;
        }
        return PaymentStatus.fromValue(status.getValue());
    }

    private static LocalDateTime toLocalDateTime(OffsetDateTime creationDate) {
        if (creationDate == null) {
            return null;
        }
        return creationDate.toLocalDateTime();
    }
}
