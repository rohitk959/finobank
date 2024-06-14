package com.finobank.accounts.adapter.database.factory;

import com.finobank.accounts.adapter.database.entity.AccountEntity;
import com.finobank.accounts.core.domain.Account;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DbAccountFactory {
    public static AccountEntity toEntity(Account account) {
        return AccountEntity.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .accountName(account.getAccountName())
                .balances(DbBalanceFactory.entities(account.getBalances()))
                .status(account.getStatus())
                .users(users(account))
                .createdAt(account.getCreatedAt())
                .createdBy(account.getCreatedBy())
                .updatedAt(account.getUpdatedAt())
                .updatedBy(account.getUpdatedBy())
                .build();
    }

    private static String users(Account account) {
        return account.getUsers().stream()
                .map(UUID::toString)
                .collect(Collectors.joining(","));
    }
}
