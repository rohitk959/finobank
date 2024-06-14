package com.finobank.accounts.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Accessors(chain = true)
public class Account {
    private UUID id;
    private String accountName;
    private String accountNumber;
    private List<Balance> balances;
    private AccountStatus status;
    private Set<UUID> users;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
