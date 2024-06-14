package com.finobank.accounts.adapter.database.entity;

import com.finobank.accounts.core.domain.AccountStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BalanceEntity> balances;

    @Column(name = "STATUS")
    private AccountStatus status;

    @Column(name = "users")
    private String users;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "CREATED_BY", nullable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "UPDATED_BY")
    private String updatedBy;
}
