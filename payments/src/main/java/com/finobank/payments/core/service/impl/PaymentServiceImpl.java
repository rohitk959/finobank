package com.finobank.payments.core.service.impl;

import com.finobank.payments.adapter.database.entity.PaymentEntity;
import com.finobank.payments.adapter.database.factory.DbPaymentFactory;
import com.finobank.payments.adapter.database.repository.PaymentRepository;
import com.finobank.payments.adapter.feignClients.AccountsFeignClient;
import com.finobank.payments.core.domain.Payment;
import com.finobank.payments.core.exception.ApplicationBadRequestException;
import com.finobank.payments.core.exception.ApplicationEntityNotFoundException;
import com.finobank.payments.core.factory.PaymentFactory;
import com.finobank.payments.core.model.*;
import com.finobank.payments.core.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.finobank.payments.core.exception.ApplicationBaseException.CODE_FAILURE;

@AllArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final AccountsFeignClient accountsFeignClient;

    @Override
    public List<ApiPayment> getPayments(String accountNumber) {
        validateAndGetCurrentUserAccount(accountNumber);

        return paymentRepository.findAllByGiverAccountNumber(accountNumber).stream()
                .map(DbPaymentFactory::fromEntity)
                .map(PaymentFactory::api)
                .toList();
    }

    @Override
    @Transactional
    public ApiPayment makePayment(ApiPayment payment) {
        if (payment.getGiverAccountNumber().trim().equalsIgnoreCase(payment.getBeneficiaryAccountNumber().trim())) {
            throw ApplicationBadRequestException.builder()
                    .code(CODE_FAILURE).message("Giver bank account and beneficiary bank account cannot be same.").build();
        }

        ApiAccount giverAccount = validateAndGetCurrentUserAccount(payment.getGiverAccountNumber());

        ResponseEntity<ApiAccount> beneficiaryAccount = null;
        try {
            beneficiaryAccount = accountsFeignClient.getAccountDetails(payment.getBeneficiaryAccountNumber());
        } catch (ApplicationEntityNotFoundException e) {
            // PROCEED FURTHER
        }

        ApiBalance balance = ApiBalance.builder()
                .amount(payment.getAmount())
                .build();

        ResponseEntity<ApiBalance> debitResponse = accountsFeignClient.updateBalance(ApiBalanceEntry.DEBIT, giverAccount.getAccountNumber(), balance);

        if (!debitResponse.getStatusCode().is2xxSuccessful()) {
            throw ApplicationBadRequestException.builder()
                    .code(CODE_FAILURE).message("Payment failed.")
                    .build();
        }

        if (Objects.nonNull(beneficiaryAccount) && beneficiaryAccount.getStatusCode().is2xxSuccessful() && beneficiaryAccount.hasBody()) {
            accountsFeignClient.updateBalance(ApiBalanceEntry.CREDIT, payment.getBeneficiaryAccountNumber(), balance);
        }

        payment.setCurrency("EUR");
        payment.setStatus(ApiPaymentStatus.EXECUTED);

        Payment paymentCore = PaymentFactory.core(payment);
        PaymentEntity savedPayment = paymentRepository.save(DbPaymentFactory.toEntity(paymentCore));

        return PaymentFactory.api(DbPaymentFactory.fromEntity(savedPayment));
    }

    private ApiAccount validateAndGetCurrentUserAccount(String accountNumber) {
        ResponseEntity<List<ApiAccount>> userAccounts = accountsFeignClient.getAccounts();

        if (!userAccounts.getStatusCode().is2xxSuccessful() || userAccounts.getBody() == null || userAccounts.getBody().isEmpty()) {
            throw ApplicationBadRequestException.builder()
                    .code(CODE_FAILURE).message("No bank accounts registered for current user.").build();
        }

        Optional<ApiAccount> userAccount = userAccounts.getBody().stream()
                .filter(a -> a.getAccountNumber().equals(accountNumber))
                .findFirst();

        if (userAccount.isEmpty()) {
            throw ApplicationBadRequestException.builder()
                    .code(CODE_FAILURE).message("Given bank account does not belong to current user.").build();
        }

        return userAccount.get();
    }
}
