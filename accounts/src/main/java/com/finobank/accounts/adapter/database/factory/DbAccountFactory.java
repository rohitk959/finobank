package com.finobank.accounts.adapter.database.factory;

import com.finobank.accounts.adapter.database.entity.AccountEntity;
import com.finobank.accounts.core.domain.Account;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DbAccountFactory {
    public static AccountEntity toEntity(Account account) {
        AccountEntity accountEntity = AccountEntity.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .accountName(account.getAccountName())
                .balances(DbBalanceFactory.toEntity(account.getBalances()))
                .status(account.getStatus())
                .users(account.getUsers())
                .createdAt(account.getCreatedAt())
                .createdBy(account.getCreatedBy())
                .updatedAt(account.getUpdatedAt())
                .updatedBy(account.getUpdatedBy())
                .build();
        accountEntity.getBalances().forEach(balance -> balance.setAccount(accountEntity));
        return accountEntity;
    }

    public static Account fromEntity(AccountEntity account) {
        return Account.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .accountName(account.getAccountName())
                .balances(DbBalanceFactory.fromEntity(account.getBalances()))
                .status(account.getStatus())
                .users(account.getUsers())
                .createdAt(account.getCreatedAt())
                .createdBy(account.getCreatedBy())
                .updatedAt(account.getUpdatedAt())
                .updatedBy(account.getUpdatedBy())
                .build();
    }
}
