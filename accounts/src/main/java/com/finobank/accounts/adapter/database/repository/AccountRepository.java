package com.finobank.accounts.adapter.database.repository;

import com.finobank.accounts.adapter.database.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    @Query("SELECT a FROM AccountEntity a JOIN a.users u WHERE u = :userId")
    List<AccountEntity> findByUserId(@Param("userId") UUID userId);

    Optional<AccountEntity> findByAccountNumber(String accountNumber);
}
