package com.khoders.asset.entities.accounting;

import javax.persistence.*;

@Entity
@Table(name = "invoice_payment")
public class InvoicePayment extends BillInvoicePayment {

    @JoinColumn(name = "invoice", referencedColumnName = "id")
    @ManyToOne
    private Invoice invoice;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
