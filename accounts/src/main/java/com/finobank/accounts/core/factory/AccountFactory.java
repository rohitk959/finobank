package com.finobank.accounts.core.factory;

import com.finobank.accounts.core.domain.Account;
import com.finobank.accounts.core.model.ApiAccount;

import java.util.UUID;

public class AccountFactory {
    public static Account fromApiAccount(ApiAccount apiAccount) {
        return Account.builder()
                .accountName(apiAccount.getAccountName())
                .accountNumber(apiAccount.getAccountNumber())
                .id(UUID.fromString(apiAccount.getId()))
                .build();
    }
}
