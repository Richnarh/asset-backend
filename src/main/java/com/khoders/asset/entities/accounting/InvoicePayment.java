package com.khoders.asset.entities.accounting;

import com.khoders.asset.entities.Ref;
import com.khoders.resource.enums.PaymentMethod;
import com.khoders.resource.enums.PaymentStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "invoice_payment")
public class InvoicePayment extends Ref {
    @Column(name = "amount")
    private double amount;

    @Column(name = "paid_date")
    private LocalDate paidDate;

    @Column(name = "amount_remaining")
    private double amountRemaining;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @JoinColumn(name = "invoice", referencedColumnName = "id")
    @ManyToOne
    private Invoice invoice;

}
