package com.khoders.asset.dto.accounting;

import com.khoders.asset.dto.BaseDto;

import java.util.LinkedList;
import java.util.List;

public class BillDto extends BaseDto {
    private String businessClient;
    private String businessClientId;
    private String receiptNo;
    private String billDate;
    private String memo;
    private double totalAmount;
    private double balanceOverDue;
    private List<BillItemDto> billItemList = new LinkedList<>();

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

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getBalanceOverDue() {
        return balanceOverDue;
    }

    public void setBalanceOverDue(double balanceOverDue) {
        this.balanceOverDue = balanceOverDue;
    }

    public List<BillItemDto> getBillItemList() {
        return billItemList;
    }

    public void setBillItemList(List<BillItemDto> billItemList) {
        this.billItemList = billItemList;
    }
}
