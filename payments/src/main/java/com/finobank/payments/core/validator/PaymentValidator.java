package com.finobank.payments.core.validator;

import com.finobank.payments.adapter.thirdparty.IbanValidator;
import com.finobank.payments.core.exception.ApplicationValidationException;
import com.finobank.payments.core.model.ApiAccount;
import com.finobank.payments.core.model.ApiPayment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class PaymentValidator {
    private final IbanValidator ibanValidator;

    public void validate(ApiPayment payment, List<ApiAccount> userAccounts) {
        List<String> errors = new ArrayList<>();

        if (payment.getGiverAccountNumber().trim().equalsIgnoreCase(payment.getBeneficiaryAccountNumber().trim())) {
            errors.add("Giver bank account and beneficiary bank account cannot be same.");
        }

        if (!ibanValidator.isValid(payment.getGiverAccountNumber())) {
            errors.add("Giver account number is invalid.");
        }

        if (!ibanValidator.isValid(payment.getBeneficiaryAccountNumber())) {
            errors.add("Beneficiary account number is invalid.");
        }

        boolean accountMatches = userAccounts.stream()
                .anyMatch(a -> a.getAccountNumber().equals(payment.getGiverAccountNumber()));

        if (!accountMatches) {
            errors.add("Given bank account does not belong to current user.");
        }

        if (!errors.isEmpty()) {
            throw new ApplicationValidationException(errors);
        }
    }

    public void validate(String accountNumber, List<ApiAccount> userAccounts) {
        List<String> errors = new ArrayList<>();

        boolean accountMatches = userAccounts.stream().anyMatch(a -> a.getAccountNumber().equals(accountNumber));

        if (!accountMatches) {
            errors.add("Given bank account does not belong to current user.");
        }

        if (!errors.isEmpty()) {
            throw new ApplicationValidationException(errors);
        }
    }
}
