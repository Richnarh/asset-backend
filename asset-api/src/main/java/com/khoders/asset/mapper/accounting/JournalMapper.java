package com.khoders.asset.mapper.accounting;

import com.khoders.asset.dto.accounting.JournalDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.entities.accounting.Account;
import com.khoders.entities.accounting.Journal;
import com.khoders.entities.constants.DebitCredit;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JournalMapper {
    @Autowired
    private AppService appService;
    public Journal toEntity(JournalDto dto) throws Exception {
        Journal journal = new Journal();
        if (dto.getId() != null) {
            journal.setId(dto.getId());
        }
        if (dto.getAccountId() == null) {
            throw new DataNotFoundException("Specify Valid AccountId");
        }
        Account account = appService.findById(Account.class, dto.getAccountId());
        if (account != null) {
            journal.setAccount(account);
        }
        journal.setAmount(dto.getAmount());
        journal.setCredit(dto.getCredit());
        journal.setDebit(dto.getDebit());
        journal.setDescription(dto.getDescription());
        journal.setDebitCredit(DebitCredit.valueOf(dto.getDebitCredit()));
        return journal;
    }

    public JournalDto toDto(Journal journal) {
        JournalDto dto = new JournalDto();
        if (journal == null) {
            return null;
        }
        dto.setId(journal.getId());
        if (journal.getAccount() != null) {
            dto.setAccountName(journal.getAccount().getAccountName());
            dto.setAccountId(journal.getAccount().getId());
        }
        dto.setAmount(journal.getAmount());
        dto.setCredit(journal.getCredit());
        dto.setDebit(journal.getDebit());
        dto.setDescription(journal.getDescription());
        dto.setDebitCredit(journal.getDebitCredit().getLabel());

        return dto;
    }
}
