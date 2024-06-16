package com.finobank.accounts.core.service.impl;

import com.finobank.accounts.adapter.database.entity.AccountEntity;
import com.finobank.accounts.adapter.database.entity.BalanceEntity;
import com.finobank.accounts.adapter.database.factory.DbBalanceFactory;
import com.finobank.accounts.adapter.database.repository.AccountRepository;
import com.finobank.accounts.adapter.database.repository.BalanceRepository;
import com.finobank.accounts.core.domain.BalanceType;
import com.finobank.accounts.core.exception.ApplicationBadRequestException;
import com.finobank.accounts.core.exception.ApplicationEntityNotFoundException;
import com.finobank.accounts.core.factory.BalanceFactory;
import com.finobank.accounts.core.model.ApiBalance;
import com.finobank.accounts.core.model.ApiBalanceEntry;
import com.finobank.accounts.core.service.BalanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

import static com.finobank.accounts.core.exception.ApplicationBaseException.CODE_FAILURE;

@AllArgsConstructor
@Service
public class BalanceServiceImpl implements BalanceService {
    private final AccountRepository accountRepository;
    private final BalanceRepository balanceRepository;

    @Override
    public ApiBalance updateBalance(ApiBalanceEntry balanceEntry, String accountNumber, ApiBalance balance) {
        Optional<AccountEntity> account = accountRepository.findByAccountNumber(accountNumber);

        if (account.isEmpty()) {
            throw new ApplicationEntityNotFoundException(accountNumber);
        }

        BalanceEntity updatedBalance;
        if (balanceEntry.equals(ApiBalanceEntry.CREDIT)) {
            updatedBalance = creditBalance(account.get(), balance);
        } else {
            updatedBalance = debitBalance(account.get(), balance);
        }
        
        return BalanceFactory.api(DbBalanceFactory.fromEntity(updatedBalance));
    }

    private BalanceEntity creditBalance(AccountEntity accountEntity, ApiBalance balance) {
        BalanceEntity availableBalance = accountEntity.getBalances().stream()
                .filter(b -> b.getType().equals(BalanceType.AVAILABLE))
                .max(Comparator.comparing(BalanceEntity::getCreatedAt)).get();

        BalanceEntity balanceEntry = BalanceEntity.builder()
                .account(accountEntity)
                .amount(availableBalance.getAmount().add(balance.getAmount()))
                .currency("EUR")
                .type(BalanceType.AVAILABLE)
                .build();

        return balanceRepository.save(balanceEntry);
    }

    private BalanceEntity debitBalance(AccountEntity accountEntity, ApiBalance balance) {
        BalanceEntity availableBalance = accountEntity.getBalances().stream()
                .filter(b -> b.getType().equals(BalanceType.AVAILABLE))
                .max(Comparator.comparing(BalanceEntity::getCreatedAt)).get();

        if (balance.getAmount().compareTo(availableBalance.getAmount()) > 0) {
            throw ApplicationBadRequestException.builder()
                    .code(CODE_FAILURE).message("Insufficient funds.").build();
        }

        BalanceEntity balanceEntry = BalanceEntity.builder()
                .account(accountEntity)
                .amount(availableBalance.getAmount().subtract(balance.getAmount()))
                .currency("EUR")
                .type(BalanceType.AVAILABLE)
                .build();

        return balanceRepository.save(balanceEntry);
    }
}
