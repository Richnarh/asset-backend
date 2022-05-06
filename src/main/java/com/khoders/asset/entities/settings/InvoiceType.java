package com.khoders.asset.entities.settings;

import com.khoders.asset.entities.Ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "invoice_type")
public class InvoiceType extends Ref {
    @Column(name = "type_name")
    private String typeName;
}
