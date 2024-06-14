package com.finobank.accounts.core.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class Balance {
    private UUID id;
    private BigDecimal amount;
    private String currency;
    private BalanceType balanceType;
    private LocalDateTime createdAt;
    private UUID createdBy;
}
