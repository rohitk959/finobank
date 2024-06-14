package com.finobank.accounts.adapter.database.entity;

import com.finobank.accounts.core.domain.BalanceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "balance")
@EntityListeners(AuditingEntityListener.class)
public class BalanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private AccountEntity account;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "TYPE", nullable = false)
    private BalanceType type;

    @CreatedDate
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;
}
