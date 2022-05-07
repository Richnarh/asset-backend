package com.khoders.asset.entities.accounting;

import javax.persistence.*;

@Entity
@Table(name = "bill_item")
public class BillItem extends BillInvoice {
    @JoinColumn(name = "bills", referencedColumnName = "id")
    @ManyToOne
    private Bill bill;

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
