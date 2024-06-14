package com.finobank.accounts.core.factory;

import com.finobank.accounts.core.domain.Balance;
import com.finobank.accounts.core.domain.BalanceType;
import com.finobank.accounts.core.model.ApiBalance;
import com.finobank.accounts.core.model.ApiBalanceType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BalanceFactory {
    public static Balance core(ApiBalance balance) {
        return Balance.builder()
                .id(balance.getId())
                .amount(balance.getAmount())
                .currency(balance.getCurrency())
                .type(BalanceType.fromValue(balance.getType().getValue()))
                .build();
    }

    public static List<Balance> core(List<ApiBalance> balances) {

        if (Objects.isNull(balances)) {
            return Collections.emptyList();
        }

        return balances.stream()
                .map(BalanceFactory::core)
                .toList();
    }

    public static Balance init() {
        return Balance.builder()
                .amount(BigDecimal.ZERO)
                .currency("EUR")
                .type(BalanceType.AVAILABLE)
                .build();
    }

    public static ApiBalance api(Balance balance) {
        return ApiBalance.builder()
                .id(balance.getId())
                .amount(balance.getAmount())
                .currency(balance.getCurrency())
                .type(ApiBalanceType.fromValue(balance.getType().getValue()))
                .build();
    }

    public static List<ApiBalance> api(List<Balance> balances) {

        if (Objects.isNull(balances)) {
            return Collections.emptyList();
        }

        return balances.stream()
                .map(BalanceFactory::api)
                .toList();
    }
}
