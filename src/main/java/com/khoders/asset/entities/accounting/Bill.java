package com.khoders.asset.entities.accounting;

import com.khoders.asset.entities.BusinessClient;
import com.khoders.asset.entities.Ref;
import com.khoders.resource.enums.Title;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bills")
public class Bill extends Ref {
    @JoinColumn(name = "business_client", referencedColumnName = "id")
    @ManyToOne
    private BusinessClient businessClient;

    @Column(name = "receipt_no")
    private String receiptNo;

    @Column(name = "bill_date")
    private LocalDate billDate;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "memo")
    @Lob
    private String memo;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "balance_overdue")
    private double balanceOverDue;

    public BusinessClient getBusinessClient() {
        return businessClient;
    }

    public void setBusinessClient(BusinessClient businessClient) {
        this.businessClient = businessClient;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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
}
