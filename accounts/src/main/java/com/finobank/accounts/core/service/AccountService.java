package com.finobank.accounts.core.service;

import com.finobank.accounts.core.model.ApiAccount;

import java.util.List;

public interface AccountService {
    ApiAccount createAccount(ApiAccount account);

    List<ApiAccount> findAccounts();

    ApiAccount getAccountDetails(String accountNumber);
}
