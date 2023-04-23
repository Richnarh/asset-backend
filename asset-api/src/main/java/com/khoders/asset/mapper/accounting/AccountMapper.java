package com.khoders.asset.mapper.accounting;

import com.khoders.asset.dto.accounting.AccountDto;
import com.khoders.entities.accounting.Account;
import com.khoders.entities.constants.AccountType;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toEntity(AccountDto dto) {
        Account account = new Account();
        if (dto.getId() != null) {
            account.setId(dto.getId());
        }
        account.setAccountCode(dto.getAccountCode());
        account.setAccountName(dto.getAccountName());
        account.setDescription(dto.getDescription());
        if (dto.isSubAccount()) {
            account.setParentAccount(dto.getAccountName());
        }
        try {
            account.setAccountType(AccountType.valueOf(dto.getAccountType()));
        } catch (Exception ignored) {
        }
        return account;
    }

    public AccountDto tDto(Account account) {
        AccountDto dto = new AccountDto();
        if (account.getId() == null) return null;
        dto.setId(account.getId());
        dto.setAccountCode(account.getAccountCode());
        dto.setAccountName(account.getAccountName());
        dto.setSubAccount(account.isSubAccount());
        dto.setParentAccount(account.getParentAccount());
        dto.setAccountType(account.getAccountType().getLabel());
        dto.setDescription(account.getDescription());
        return dto;
    }
}
