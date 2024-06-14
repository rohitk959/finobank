package com.finobank.accounts.core.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Account {
    private UUID id;
    private String accountName;
    private String accountNumber;
    private List<Balance> balances;
    private AccountStatus status;
    private List<UUID> users;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
