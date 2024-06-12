package com.finobank.accounts.core.service.impl;

import com.finobank.accounts.adapter.database.repository.AccountRepository;
import com.finobank.accounts.adapter.database.repository.BalanceRepository;
import com.finobank.accounts.core.domain.Account;
import com.finobank.accounts.core.factory.AccountFactory;
import com.finobank.accounts.core.model.ApiAccount;
import com.finobank.accounts.core.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final BalanceRepository balanceRepository;

    @Override
    public ApiAccount createAccount(ApiAccount account) {
        Account coreEntity = AccountFactory.fromApiAccount(account);
        /*accountRepository.save(coreEntity);
        balanceRepository.save();*/
        return null;
    }
}
