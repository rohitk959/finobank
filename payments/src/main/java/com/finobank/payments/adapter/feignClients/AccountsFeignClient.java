package com.finobank.payments.adapter.feignClients;

import com.finobank.payments.core.config.FeignConfig;
import com.finobank.payments.core.model.ApiAccount;
import com.finobank.payments.core.model.ApiBalance;
import com.finobank.payments.core.model.ApiBalanceEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "accounts", configuration = FeignConfig.class)
public interface AccountsFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/accounts", produces = {"application/json"})
    ResponseEntity<List<ApiAccount>> getAccounts();

    @RequestMapping(method = RequestMethod.GET, value = "/accounts/{accountNumber}", produces = {"application/json"})
    ResponseEntity<ApiAccount> getAccountDetails(@PathVariable("accountNumber") String accountNumber);

    @RequestMapping(method = RequestMethod.PUT, value = "/balances/{balanceEntry}/{accountNumber}", produces = {"application/json"}, consumes = {"application/json"})
    ResponseEntity<ApiBalance> updateBalance(@PathVariable("balanceEntry") ApiBalanceEntry balanceEntry,
                                             @PathVariable("accountNumber") String accountNumber,
                                             @RequestBody ApiBalance apiBalance
    );
}
