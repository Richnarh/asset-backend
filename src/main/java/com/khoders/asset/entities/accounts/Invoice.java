package com.khoders.asset.entities.accounts;

import com.khoders.asset.entities.Ref;
import com.khoders.asset.entities.Vendor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class Invoice  extends Ref {
    @JoinColumn(name = "vendor", referencedColumnName = "id")
    @ManyToOne
    private Vendor vendor;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "memo")
    @Lob
    private String memo;

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
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
}
