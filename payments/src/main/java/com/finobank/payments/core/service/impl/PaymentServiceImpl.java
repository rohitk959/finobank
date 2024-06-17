package com.finobank.payments.core.service.impl;

import com.finobank.payments.adapter.database.entity.PaymentEntity;
import com.finobank.payments.adapter.database.factory.DbPaymentFactory;
import com.finobank.payments.adapter.database.repository.PaymentRepository;
import com.finobank.payments.core.domain.Payment;
import com.finobank.payments.core.exception.ApplicationEntityNotFoundException;
import com.finobank.payments.core.factory.PaymentFactory;
import com.finobank.payments.core.model.ApiAccount;
import com.finobank.payments.core.model.ApiBalance;
import com.finobank.payments.core.model.ApiPayment;
import com.finobank.payments.core.service.PaymentService;
import com.finobank.payments.core.service.helper.PaymentServiceHelper;
import com.finobank.payments.core.validator.PaymentValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentServiceHelper paymentServiceHelper;
    private final PaymentValidator paymentValidator;

    @Override
    public List<ApiPayment> getPayments(String accountNumber) {
        List<ApiAccount> userAccounts = paymentServiceHelper.getUserAccounts();

        paymentValidator.validate(accountNumber, userAccounts);

        return paymentRepository.findAllByGiverAccountNumber(accountNumber).stream()
                .map(DbPaymentFactory::fromEntity)
                .map(PaymentFactory::api)
                .toList();
    }

    @Override
    @Transactional
    public ApiPayment makePayment(ApiPayment payment) {
        paymentServiceHelper.preparePayment(payment);

        List<ApiAccount> userAccounts = paymentServiceHelper.getUserAccounts();

        paymentValidator.validate(payment, userAccounts);

        ApiBalance balance = ApiBalance.builder()
                .amount(payment.getAmount())
                .build();

        paymentServiceHelper.debitGiverAccount(payment, balance);

        paymentServiceHelper.creditBeneficiaryAccount(payment, balance);

        Payment paymentCore = PaymentFactory.core(payment);
        PaymentEntity savedPayment = paymentRepository.save(DbPaymentFactory.toEntity(paymentCore));

        return PaymentFactory.api(DbPaymentFactory.fromEntity(savedPayment));
    }

    @Override
    @Transactional
    public void deletePaymentById(UUID paymentId) {
        Optional<PaymentEntity> payment = paymentRepository.findById(paymentId);

        if (payment.isEmpty()) {
            throw new ApplicationEntityNotFoundException(paymentId.toString());
        }

        List<ApiAccount> userAccounts = paymentServiceHelper.getUserAccounts();

        paymentValidator.validate(payment.get().getGiverAccountNumber(), userAccounts);

        paymentRepository.delete(payment.get());
    }
}
