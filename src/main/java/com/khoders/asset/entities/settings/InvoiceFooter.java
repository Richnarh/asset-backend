package com.khoders.asset.entities.settings;

import com.khoders.asset.entities.Ref;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "invoice_footer")
public class InvoiceFooter extends Ref {
    @JoinColumn(name = "invoice_type", referencedColumnName = "id")
    @ManyToOne
    private InvoiceType invoiceType;
}
