package com.finobank.accounts.core.service.impl;

import com.finobank.accounts.adapter.database.entity.AccountEntity;
import com.finobank.accounts.adapter.database.factory.DbAccountFactory;
import com.finobank.accounts.adapter.database.repository.AccountRepository;
import com.finobank.accounts.core.domain.Account;
import com.finobank.accounts.core.domain.AccountStatus;
import com.finobank.accounts.core.factory.AccountFactory;
import com.finobank.accounts.core.factory.BalanceFactory;
import com.finobank.accounts.core.model.ApiAccount;
import com.finobank.accounts.core.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public ApiAccount createAccount(ApiAccount account) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Account coreAccount = AccountFactory.core(account)
                .setBalances(List.of(BalanceFactory.init()))
                .setStatus(AccountStatus.ENABLED)
                .setUsers(Set.of(UUID.fromString(userId)));

        AccountEntity accountEntity = DbAccountFactory.toEntity(coreAccount);

        AccountEntity savedAccount = accountRepository.save(accountEntity);

        coreAccount = DbAccountFactory.fromEntity(savedAccount);

        return AccountFactory.api(coreAccount);
    }

    @Override
    public List<ApiAccount> findAccounts() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<AccountEntity> accountEntities = accountRepository.findByUserId(UUID.fromString(userId));
        return accountEntities.stream()
                .map(DbAccountFactory::fromEntity)
                .map(AccountFactory::api)
                .toList();
    }
}
