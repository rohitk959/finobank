package com.finobank.payments.core.service.helper;

import com.finobank.payments.adapter.database.entity.PaymentEntity;
import com.finobank.payments.adapter.database.factory.DbPaymentFactory;
import com.finobank.payments.adapter.database.repository.PaymentRepository;
import com.finobank.payments.adapter.feignClients.AccountsFeignClient;
import com.finobank.payments.core.domain.Payment;
import com.finobank.payments.core.domain.PaymentStatus;
import com.finobank.payments.core.exception.ApplicationBadRequestException;
import com.finobank.payments.core.exception.ApplicationBaseException;
import com.finobank.payments.core.model.ApiAccount;
import com.finobank.payments.core.model.ApiBalance;
import com.finobank.payments.core.model.ApiBalanceEntry;
import com.finobank.payments.core.model.ApiPayment;
import com.finobank.payments.core.model.ApiPaymentStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.finobank.payments.core.exception.ApplicationBaseException.CODE_FAILURE;

@AllArgsConstructor
@Component
public class PaymentServiceHelper {
    private final AccountsFeignClient accountsFeignClient;
    private final PaymentRepository paymentRepository;

    public void debitGiverAccount(Payment payment, ApiBalance balance) {
        ResponseEntity<ApiBalance> debitResponse = accountsFeignClient.updateBalance(ApiBalanceEntry.DEBIT, payment.getGiverAccountNumber(), balance);

        if (!debitResponse.getStatusCode().is2xxSuccessful()) {
            throw ApplicationBadRequestException.builder()
                    .code(CODE_FAILURE).message("Payment failed.")
                    .build();
        }
    }

    public void creditBeneficiaryAccount(Payment payment, ApiBalance balance) {
        try {
            ResponseEntity<ApiAccount> beneficiaryAccount = accountsFeignClient.getAccountDetails(payment.getBeneficiaryAccountNumber());

            if (beneficiaryAccount.getStatusCode().is2xxSuccessful() && beneficiaryAccount.hasBody()) {
                accountsFeignClient.updateBalance(ApiBalanceEntry.CREDIT, payment.getBeneficiaryAccountNumber(), balance);
            }
        } catch (ApplicationBaseException e) {
            // PROCEED FURTHER
        }
    }

    public List<ApiAccount> getUserAccounts() {
        ResponseEntity<List<ApiAccount>> userAccounts = accountsFeignClient.getAccounts();

        if (!userAccounts.getStatusCode().is2xxSuccessful() || userAccounts.getBody() == null || userAccounts.getBody().isEmpty()) {
            throw ApplicationBadRequestException.builder()
                    .code(CODE_FAILURE).message("No bank accounts registered for current user.").build();
        }

        return userAccounts.getBody();
    }

    public void preparePayment(ApiPayment payment) {
        payment.setCurrency("EUR");
        payment.setStatus(ApiPaymentStatus.EXECUTED);
        payment.setFraudulentTransaction(false);
    }

    public void checkAndBlockAccount(Payment payment) {
        payment.setFraudulentTransaction(true);
        payment.setStatus(PaymentStatus.BLOCKED);
        List<PaymentEntity> payments = paymentRepository.findAllByGiverAccountNumberAndFraudulentTransactionTrue(payment.getGiverAccountNumber());

        if (payments.size() < 5) {
            return;
        }

        paymentRepository.save(DbPaymentFactory.toEntity(payment));
        accountsFeignClient.blockAccount(payment.getGiverAccountNumber());

        throw new ApplicationBadRequestException(CODE_FAILURE, "Giver account blocked.");
    }
}
