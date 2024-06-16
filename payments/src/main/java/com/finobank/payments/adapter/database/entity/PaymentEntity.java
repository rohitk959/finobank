package com.finobank.payments.adapter.database.entity;

import com.finobank.payments.core.domain.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "T_PAYMENT")
@EntityListeners(AuditingEntityListener.class)
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Column(name = "BENEFICIARY_ACCOUNT_NUMBER", nullable = false)
    private String beneficiaryAccountNumber;

    @Column(name = "COMMUNICATION")
    private String communication;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "CURRENCY", nullable = false)
    private String currency;

    @Column(name = "FRAUDULENT_TXN", nullable = false)
    private boolean fraudulentTransaction;

    @Column(name = "GIVER_ACCOUNT_NUMBER", nullable = false)
    private String giverAccountNumber;

    @Column(name = "STATUS", nullable = false)
    private PaymentStatus status;


}
