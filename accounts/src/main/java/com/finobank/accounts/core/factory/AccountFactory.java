package com.finobank.accounts.core.factory;

import com.finobank.accounts.core.domain.Account;
import com.finobank.accounts.core.domain.AccountStatus;
import com.finobank.accounts.core.model.ApiAccount;
import com.finobank.accounts.core.model.ApiUser;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AccountFactory {
    public static Account core(ApiAccount account) {
        return Account.builder()
                .accountName(account.getAccountName())
                .accountNumber(account.getAccountNumber())
                .id(account.getId())
                .balances(BalanceFactory.fromApi(account.getBalance()))
                .status(status(account))
                .users(getUsers(account))
                .build();
    }

    private static AccountStatus status(ApiAccount account) {
        if (account.getStatus() == null)
            return null;
        return AccountStatus.fromValue(account.getStatus().getValue());
    }

    private static List<UUID> getUsers(ApiAccount account) {
        if (Objects.isNull(account.getUsers())) {
            return Collections.emptyList();
        }
        return account.getUsers().stream().map(ApiUser::getId).toList();
    }
}
