package com.finobank.payments.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Accessors(chain = true)
public class Payment {
    private UUID id;
    private BigDecimal amount;
    private String currency;
    private String giverAccountNumber;
    private String beneficiaryAccountNumber;
    private String communication;
    private LocalDateTime creationDate;
    private PaymentStatus status;
}
