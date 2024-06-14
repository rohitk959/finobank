package com.finobank.accounts.adapter.database.factory;

import com.finobank.accounts.adapter.database.entity.BalanceEntity;
import com.finobank.accounts.core.domain.Balance;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DbBalanceFactory {
    public static BalanceEntity toEntity(Balance balance) {
        return BalanceEntity.builder()
                .id(balance.getId())
                .amount(balance.getAmount())
                .currency(balance.getCurrency())
                .type(balance.getType())
                .build();
    }

    public static List<BalanceEntity> toEntity(List<Balance> balances) {
        if (balances == null || balances.isEmpty()) {
            return Collections.emptyList();
        }

        return balances.stream()
                .map(DbBalanceFactory::toEntity)
                .toList();
    }

    public static Balance fromEntity(BalanceEntity balance) {
        return Balance.builder()
                .id(balance.getId())
                .amount(balance.getAmount())
                .currency(balance.getCurrency())
                .type(balance.getType())
                .build();
    }

    public static List<Balance> fromEntity(List<BalanceEntity> balances) {
        if (balances == null || balances.isEmpty()) {
            return Collections.emptyList();
        }

        return balances.stream()
                .map(DbBalanceFactory::fromEntity)
                .toList();
    }
}
