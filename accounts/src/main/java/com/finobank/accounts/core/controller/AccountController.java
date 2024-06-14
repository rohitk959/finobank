package com.finobank.accounts.core.controller;

import com.finobank.accounts.core.api.AccountsApi;
import com.finobank.accounts.core.model.ApiAccount;
import com.finobank.accounts.core.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class AccountController implements AccountsApi {
    private final AccountService accountService;

    @Override
    @PreAuthorize("hasRole('finobank_user')")
    public ResponseEntity<ApiAccount> createAccount(ApiAccount apiAccount) {
        ApiAccount accountCreated = accountService.createAccount(apiAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountCreated);
    }

    @Override
    @PreAuthorize("hasRole('finobank_user')")
    public ResponseEntity<List<ApiAccount>> getAccounts() {
        List<ApiAccount> accounts = accountService.findAccounts();
        return ResponseEntity.ok(accounts);
    }
}
