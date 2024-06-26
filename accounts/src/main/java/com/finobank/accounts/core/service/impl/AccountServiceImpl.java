package com.finobank.accounts.core.service.impl;

import com.finobank.accounts.adapter.database.entity.AccountEntity;
import com.finobank.accounts.adapter.database.factory.DbAccountFactory;
import com.finobank.accounts.adapter.database.repository.AccountRepository;
import com.finobank.accounts.adapter.feignclients.UsersFeignClient;
import com.finobank.accounts.adapter.thirdparty.IbanValidator;
import com.finobank.accounts.core.domain.Account;
import com.finobank.accounts.core.domain.AccountStatus;
import com.finobank.accounts.core.exception.ApplicationBadRequestException;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.finobank.accounts.core.exception.ApplicationBaseException.CODE_FAILURE;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UsersFeignClient usersFeignClient;
    private final IbanValidator ibanValidator;

    @Override
    @Transactional
    public ApiAccount createAccount(ApiAccount account) {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        if (!ibanValidator.isValid(account.getAccountNumber())) {
            throw new ApplicationBadRequestException(CODE_FAILURE, "Invalid iban number");
        }

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
                .map(this::fetchUsers)
                .toList();
    }

    @Override
    public ApiAccount getAccountDetails(String accountNumber) {
        if (!ibanValidator.isValid(accountNumber)) {
            throw new ApplicationBadRequestException(CODE_FAILURE, "Invalid iban number");
        }

        Optional<AccountEntity> account = accountRepository.findByAccountNumber(accountNumber);

        if (account.isEmpty()) {
            throw new ApplicationEntityNotFoundException(accountNumber);
        }

        Account coreAccount = DbAccountFactory.fromEntity(account.get());

        return AccountFactory.api(coreAccount);
    }

    @Override
    public ApiAccount blockAccount(String accountNumber) {
        if (!ibanValidator.isValid(accountNumber)) {
            throw new ApplicationBadRequestException(CODE_FAILURE, "Invalid iban number");
        }

        Optional<AccountEntity> account = accountRepository.findByAccountNumber(accountNumber);

        if (account.isEmpty()) {
            throw new ApplicationEntityNotFoundException(accountNumber);
        }

        account.get().setStatus(AccountStatus.BLOCKED);
        AccountEntity savedAccount = accountRepository.save(account.get());
        
        return AccountFactory.api(DbAccountFactory.fromEntity(savedAccount));
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
