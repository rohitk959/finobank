package com.finobank.accounts.core.service.impl;

import com.finobank.accounts.adapter.database.entity.AccountEntity;
import com.finobank.accounts.adapter.database.entity.BalanceEntity;
import com.finobank.accounts.adapter.database.factory.DbAccountFactory;
import com.finobank.accounts.adapter.database.repository.AccountRepository;
import com.finobank.accounts.adapter.database.repository.BalanceRepository;
import com.finobank.accounts.core.domain.Account;
import com.finobank.accounts.core.domain.AccountStatus;
import com.finobank.accounts.core.domain.BalanceType;
import com.finobank.accounts.core.factory.AccountFactory;
import com.finobank.accounts.core.model.ApiAccount;
import com.finobank.accounts.core.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final BalanceRepository balanceRepository;

    @Override
    public ApiAccount createAccount(ApiAccount account) {
        Account coreAccount = AccountFactory.core(account);
        coreAccount.setBalances(Collections.emptyList());
        coreAccount.setStatus(AccountStatus.ENABLED);
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        coreAccount.setUsers(List.of(UUID.fromString(userId)));
        AccountEntity accountEntity = DbAccountFactory.toEntity(coreAccount);
        accountEntity = accountRepository.save(accountEntity);

        BalanceEntity balanceEntity = BalanceEntity.builder()
                .account(accountEntity)
                .amount(BigDecimal.TEN)
                .currency("EUR")
                .type(BalanceType.AVAILABLE)
                .build();
        balanceRepository.save(balanceEntity);

        return null;
    }
}
