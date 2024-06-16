package com.finobank.payments.adapter.database.factory;

import com.finobank.payments.adapter.database.entity.PaymentEntity;
import com.finobank.payments.core.domain.Payment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DbPaymentFactory {
    public static PaymentEntity toEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .beneficiaryAccountNumber(payment.getBeneficiaryAccountNumber())
                .communication(payment.getCommunication())
                .createdAt(payment.getCreationDate())
                .currency(payment.getCurrency())
                .giverAccountNumber(payment.getGiverAccountNumber())
                .status(payment.getStatus())
                .build();
    }

    public static Payment fromEntity(PaymentEntity payment) {
        return Payment.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .beneficiaryAccountNumber(payment.getBeneficiaryAccountNumber())
                .communication(payment.getCommunication())
                .creationDate(payment.getCreatedAt())
                .currency(payment.getCurrency())
                .giverAccountNumber(payment.getGiverAccountNumber())
                .status(payment.getStatus())
                .build();
    }
}
