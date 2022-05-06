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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
