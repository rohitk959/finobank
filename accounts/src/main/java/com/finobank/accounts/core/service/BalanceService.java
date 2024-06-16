package com.finobank.accounts.core.service;

import com.finobank.accounts.core.model.ApiBalance;
import com.finobank.accounts.core.model.ApiBalanceEntry;

public interface BalanceService {
    ApiBalance updateBalance(ApiBalanceEntry balanceEntry, String accountNumber, ApiBalance apiBalance);
}
