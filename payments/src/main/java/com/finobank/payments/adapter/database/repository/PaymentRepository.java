package com.finobank.payments.adapter.database.repository;

import com.finobank.payments.adapter.database.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    List<PaymentEntity> findAllByGiverAccountNumber(String accountNumber);
}
