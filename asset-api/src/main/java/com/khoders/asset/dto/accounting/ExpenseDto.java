package com.khoders.asset.dto.accounting;

import com.khoders.asset.dto.BaseDto;
import com.khoders.entities.accounting.ExpenseItem;

import java.util.LinkedList;
import java.util.List;

public class ExpenseDto extends BaseDto {
    private String expenseDate;
    private String receiptNo;
    private String accountName;
    private String accountId;
    private String businessClient;
    private String businessClientId;
    private List<ExpenseItem> expenseItemList = new LinkedList<>();

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBusinessClient() {
        return businessClient;
    }

    public void setBusinessClient(String businessClient) {
        this.businessClient = businessClient;
    }

    public String getBusinessClientId() {
        return businessClientId;
    }

    public void setBusinessClientId(String businessClientId) {
        this.businessClientId = businessClientId;
    }

    public List<ExpenseItem> getExpenseItemList() {
        return expenseItemList;
    }

    public void setExpenseItemList(List<ExpenseItem> expenseItemList) {
        this.expenseItemList = expenseItemList;
    }
}
