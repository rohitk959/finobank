package com.finobank.accounts.core.service.impl;

import com.finobank.accounts.adapter.database.entity.AccountEntity;
import com.finobank.accounts.adapter.database.factory.DbAccountFactory;
import com.finobank.accounts.adapter.database.repository.AccountRepository;
import com.finobank.accounts.adapter.feignclients.UsersFeignClient;
import com.finobank.accounts.core.domain.Account;
import com.finobank.accounts.core.domain.AccountStatus;
import com.finobank.accounts.core.domain.Balance;
import com.finobank.accounts.core.exception.ApplicationEntityNotFoundException;
import com.finobank.accounts.core.factory.AccountFactory;
import com.finobank.accounts.core.factory.BalanceFactory;
import com.finobank.accounts.core.model.ApiAccount;
import com.finobank.accounts.core.model.ApiUser;
import com.finobank.accounts.core.service.AccountService;
import com.google.common.collect.Lists;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UsersFeignClient usersFeignClient;

    @Override
    @Transactional
    public ApiAccount createAccount(ApiAccount account) {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        Account coreAccount = AccountFactory.core(account)
                .setBalances(List.of(BalanceFactory.init()))
                .setStatus(AccountStatus.ENABLED)
                .setUsers(Set.of(userId));

        AccountEntity accountEntity = DbAccountFactory.toEntity(coreAccount);

        AccountEntity savedAccount = accountRepository.save(accountEntity);

        coreAccount = DbAccountFactory.fromEntity(savedAccount);

        return fetchUsers(coreAccount);
    }

    @Override
    public List<ApiAccount> findAccounts() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<AccountEntity> accountEntities = accountRepository.findByUserId(UUID.fromString(userId));

        return accountEntities.stream()
                .map(DbAccountFactory::fromEntity)
                .peek(account -> account.setBalances(account.getBalances().stream()
                        .sorted(Comparator.comparing(Balance::getCreatedBy).reversed())
                        .toList()))
                .map(this::fetchUsers)
                .toList();
    }

    @Override
    public ApiAccount getAccountDetails(String accountNumber) {
        Optional<AccountEntity> account = accountRepository.findByAccountNumber(accountNumber);

        if (account.isEmpty()) {
            throw new ApplicationEntityNotFoundException(accountNumber);
        }

        Account coreAccount = DbAccountFactory.fromEntity(account.get());
        coreAccount.setBalances(coreAccount.getBalances().stream()
                .sorted(Comparator.comparing(Balance::getCreatedBy).reversed())
                .toList());

        return AccountFactory.api(coreAccount);
    }

    private ApiAccount fetchUsers(Account coreAccount) {
        ResponseEntity<List<ApiUser>> user = usersFeignClient.getUsers(Lists.newArrayList(coreAccount.getUsers()));

        ApiAccount apiAccount = AccountFactory.api(coreAccount);

        if (Objects.nonNull(user) && user.hasBody()) {
            apiAccount.setUsers(user.getBody());
        }
        return apiAccount;
    }
}
