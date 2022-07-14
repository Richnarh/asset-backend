package com.khoders.asset.mapper.accounting;

import com.khoders.asset.dto.accounting.GeneralLedgerDto;
import com.khoders.asset.entities.accounting.Account;
import com.khoders.asset.entities.accounting.GeneralLedger;
import com.khoders.asset.entities.constants.EntrySource;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GLMapper {
    @Autowired private CrudBuilder builder;

    public GeneralLedger toEntity(GeneralLedgerDto dto){
        GeneralLedger ledger = new GeneralLedger();
        if (dto.getId() != null) {
            ledger.setId(dto.getId());
        }
        if (dto.getAccountId() == null) {
            throw new DataNotFoundException("Specify Valid AccountId");
        }
        Account account = builder.simpleFind(Account.class, dto.getAccountId());
        if(account != null){
            ledger.setAccount(account);
        }
        ledger.setCredit(dto.getCredit());
        ledger.setDebit(dto.getDebit());
        ledger.setDescription(dto.getDescription());
        ledger.setEntryDate(DateUtil.parseLocalDate(dto.getEntryDate(), Pattern._yyyyMMdd));
        ledger.setEntrySource(EntrySource.valueOf(dto.getEntrySource()));
        return ledger;
    }

    public GeneralLedgerDto toDto(GeneralLedger ledger){
        GeneralLedgerDto dto = new GeneralLedgerDto();
        if (ledger == null) {
            return null;
        }
        dto.setCredit(ledger.getCredit());
        dto.setDebit(ledger.getDebit());
        dto.setDescription(ledger.getDescription());
        dto.setEntryDate(DateUtil.parseLocalDateString(ledger.getEntryDate(), Pattern.ddMMyyyy));
        dto.setEntrySource(ledger.getEntrySource().getLabel());
        if(ledger.getAccount() != null){
            dto.setAccountName(ledger.getAccount().getAccountName());
            dto.setAccountId(ledger.getAccount().getId());
        }
        return dto;
    }
}
