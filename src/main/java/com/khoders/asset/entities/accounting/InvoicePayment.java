package com.khoders.asset.entities.accounting;

import com.khoders.asset.entities.Ref;
import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.enums.PaymentStatus;

import javax.persistence.*;
import java.time.LocalDate;

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
