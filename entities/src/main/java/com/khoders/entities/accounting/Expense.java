package com.khoders.entities.accounting;

import com.khoders.entities.BusinessClient;
import com.khoders.entities.Ref;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "expense")
public class Expense extends Ref {
    @Column(name = "expense_date")
    private LocalDate expenseDate;

    @Column(name = "receipt_no")
    private String receiptNo;

    @JoinColumn(name = "payment_account")
    @ManyToOne
    private Account account;

    @JoinColumn(name = "business_client")
    @ManyToOne
    private BusinessClient businessClient;

    @Transient
    private List<ExpenseItem> expenseItemList = new LinkedList<>();

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BusinessClient getBusinessClient() {
        return businessClient;
    }

    public void setBusinessClient(BusinessClient businessClient) {
        this.businessClient = businessClient;
    }

    public List<ExpenseItem> getExpenseItemList() {
        return expenseItemList;
    }

    public void setExpenseItemList(List<ExpenseItem> expenseItemList) {
        this.expenseItemList = expenseItemList;
    }
}
