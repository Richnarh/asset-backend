package com.khoders.asset.mapper.accounting;

import com.khoders.asset.dto.accounting.ExpenseDto;
import com.khoders.asset.dto.accounting.ExpenseItemDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.entities.BusinessClient;
import com.khoders.entities.accounting.Account;
import com.khoders.entities.accounting.Expense;
import com.khoders.entities.accounting.ExpenseItem;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ExpenseMapper {
    @Autowired
    private AppService appService;

    public Expense toEntity(ExpenseDto dto) throws Exception {
        Expense expense = new Expense();
        if (dto.getId() != null) {
            expense.setId(dto.getId());
        }
        expense.setExpenseDate(DateUtil.parseLocalDate(dto.getExpenseDate(), Pattern._yyyyMMdd));
        expense.setReceiptNo(dto.getReceiptNo());
        if (dto.getAccountId() == null) {
            throw new DataNotFoundException("Please Specify Valid AccountId");
        }
        if (dto.getBusinessClientId() == null) {
            throw new DataNotFoundException("Please Specify Valid Client/Vendor");
        }
        Account account = appService.findById(Account.class, dto.getAccountId());
        if (account != null) {
            expense.setAccount(account);
        }
        BusinessClient businessClient = appService.findById(BusinessClient.class, dto.getBusinessClientId());
        if (businessClient != null) {
            expense.setBusinessClient(businessClient);
        }
        expense.setExpenseItemList(dto.getExpenseItemList());
        return expense;
    }

    public ExpenseDto toDto(Expense expense) {
        ExpenseDto dto = new ExpenseDto();
        if (expense.getId() == null) return null;
        dto.setId(expense.getId());
        dto.setReceiptNo(expense.getReceiptNo());
        dto.setExpenseDate(DateUtil.parseLocalDateString(expense.getExpenseDate(), Pattern.ddMMyyyy));
        if (expense.getAccount() != null) {
            dto.setAccountId(expense.getAccount().getId());
            dto.setAccountName(expense.getAccount().getAccountName());
        }
        if (expense.getBusinessClient() != null) {
            dto.setBusinessClientId(expense.getBusinessClient().getId());
            dto.setBusinessClient(expense.getBusinessClient().getPhoneNumber() + " " + expense.getBusinessClient().getFirstname());
        }
        dto.setExpenseItemList(expense.getExpenseItemList());
        return dto;
    }

    public List<ExpenseItem> toEntity(List<ExpenseItemDto> itemDtoList) throws Exception {
        List<ExpenseItem> expenseItemList = new LinkedList<>();
        for (ExpenseItemDto dto : itemDtoList) {
            ExpenseItem expenseItem = new ExpenseItem();
            if (dto.getId() != null) {
                expenseItem.setId(dto.getId());
            }
            expenseItem.setProduct(dto.getProduct());
            expenseItem.setQuantity(dto.getQuantity());
            expenseItem.setUnitPrice(dto.getUnitPrice());
            expenseItem.setTotalAmount(dto.getTotalAmount());
            if (dto.getAccountId() == null) {
                throw new DataNotFoundException("Please Specify Valid AccountId");
            }
            Account account = appService.findById(Account.class, dto.getAccountId());
            if (account != null) {
                expenseItem.setAccount(account);
            }
            expenseItemList.add(expenseItem);
        }
        return expenseItemList;
    }

    public List<ExpenseItemDto> toDto(List<ExpenseItem> expenseItemList) {
        List<ExpenseItemDto> dtoList = new LinkedList<>();
        for (ExpenseItem expenseItem : expenseItemList) {
            ExpenseItemDto dto = new ExpenseItemDto();
            if (expenseItem.getId() == null) return null;
            dto.setId(expenseItem.getId());
            dto.setProduct(expenseItem.getProduct());
            dto.setQuantity(expenseItem.getQuantity());
            dto.setUnitPrice(expenseItem.getUnitPrice());
            dto.setTotalAmount(expenseItem.getTotalAmount());
            if (expenseItem.getAccount() != null) {
                dto.setAccountId(expenseItem.getAccount().getId());
                dto.setAccountName(expenseItem.getAccount().getAccountName());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }
}
