package com.finobank.accounts.core.factory;

import com.finobank.accounts.core.domain.Account;
import com.finobank.accounts.core.domain.AccountStatus;
import com.finobank.accounts.core.model.ApiAccount;
import com.finobank.accounts.core.model.ApiAccountStatus;
import com.finobank.accounts.core.model.ApiUser;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountFactory {
    public static Account core(ApiAccount account) {
        return Account.builder()
                .accountName(account.getAccountName())
                .accountNumber(account.getAccountNumber())
                .id(account.getId())
                .balances(BalanceFactory.core(account.getBalances()))
                .status(status(account.getStatus()))
                .users(users(account))
                .build();
    }

    private static AccountStatus status(ApiAccountStatus accountStatus) {
        if (accountStatus == null)
            return null;
        return AccountStatus.fromValue(accountStatus.getValue());
    }

    private static Set<UUID> users(ApiAccount account) {
        if (Objects.isNull(account.getUsers())) {
            return Collections.emptySet();
        }
        return account.getUsers().stream().map(ApiUser::getId).collect(Collectors.toSet());
    }

    public static ApiAccount api(Account account) {
        return ApiAccount.builder()
                .accountName(account.getAccountName())
                .accountNumber(account.getAccountNumber())
                .id(account.getId())
                .balances(BalanceFactory.api(account.getBalances()))
                .status(status(account.getStatus()))
                .build();
    }

    private static ApiAccountStatus status(AccountStatus accountStatus) {
        if (accountStatus == null)
            return null;
        return ApiAccountStatus.fromValue(accountStatus.getValue());
    }
}
