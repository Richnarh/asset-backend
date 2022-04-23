package com.khoders.asset.entities.accounts;

import com.khoders.asset.entities.Ref;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

public class InvoiceItem extends Ref {

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "product")
    @Lob
    private String product;

    @JoinColumn(name = "invoice",referencedColumnName = "id")
    @ManyToOne
    private Invoice invoice;
}
