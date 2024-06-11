package com.finobank.accounts.core.controller;

import com.finobank.accounts.core.api.AccountsApi;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController implements AccountsApi {

    @Override
    @PreAuthorize("hasRole('finobank_user')")
    public ResponseEntity<String> getAccounts() {
        return ResponseEntity.ok("Hello from accounts ms.");
    }
}
