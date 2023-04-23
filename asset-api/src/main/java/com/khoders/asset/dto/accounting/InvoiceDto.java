package com.khoders.asset.dto.accounting;

import com.khoders.asset.dto.BaseDto;

import java.util.LinkedList;
import java.util.List;

public class InvoiceDto extends BaseDto {
    private String businessClient;
    private String businessClientId;
    private String invoiceNo;
    private String dueDate;
    private String memo;
    private double balanceOverDue;
    private double totalAmount;
    private String customerNotes;
    private String termsCondition;
    private List<InvoiceItemDto> invoiceItemList = new LinkedList<>();

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

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public double getBalanceOverDue() {
        return balanceOverDue;
    }

    public void setBalanceOverDue(double balanceOverDue) {
        this.balanceOverDue = balanceOverDue;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(String customerNotes) {
        this.customerNotes = customerNotes;
    }

    public String getTermsCondition() {
        return termsCondition;
    }

    public void setTermsCondition(String termsCondition) {
        this.termsCondition = termsCondition;
    }

    public List<InvoiceItemDto> getInvoiceItemList() {
        return invoiceItemList;
    }

    public void setInvoiceItemList(List<InvoiceItemDto> invoiceItemList) {
        this.invoiceItemList = invoiceItemList;
    }
}
