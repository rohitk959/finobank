package com.finobank.accounts.core.factory;

import com.finobank.accounts.core.domain.Balance;
import com.finobank.accounts.core.model.ApiBalance;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BalanceFactory {
    public static Balance fromApi(ApiBalance balance) {
        return Balance.builder()
                .id(balance.getId())
                .amount(balance.getAmount())
                .currency(balance.getCurrency())
                .build();
    }

    public static List<Balance> fromApi(List<ApiBalance> balances) {

        if (Objects.isNull(balances)) {
            return Collections.emptyList();
        }

        return balances.stream()
                .map(BalanceFactory::fromApi)
                .toList();
    }
}
