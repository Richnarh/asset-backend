package com.khoders.asset.entities.accounting;

import com.khoders.asset.entities.BusinessClient;
import com.khoders.asset.entities.Ref;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "invoices")
public class Invoice  extends Ref {
    @JoinColumn(name = "business_client", referencedColumnName = "id")
    @ManyToOne
    private BusinessClient businessClient;

    @Column(name = "customer_type")
    private boolean customerType;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "memo")
    @Lob
    private String memo;

    @Column(name = "balance_overdue")
    private double balanceOverDue;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "customer_notes")
    @Lob
    private String customerNotes;

    @Column(name = "terms_condition")
    @Lob
    private String termsCondition;

    public BusinessClient getBusinessClient() {
        return businessClient;
    }

    public void setBusinessClient(BusinessClient businessClient) {
        this.businessClient = businessClient;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
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
}
