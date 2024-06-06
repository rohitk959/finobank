package com.finobank.accounts.core.controller;

import com.finobank.accounts.core.api.AccountsApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController implements AccountsApi {

    @Override
    public ResponseEntity<String> getAccounts() {
        return AccountsApi.super.getAccounts();
    }
}
