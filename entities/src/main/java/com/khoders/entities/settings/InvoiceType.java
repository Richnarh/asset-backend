package com.khoders.entities.settings;

import com.khoders.entities.Ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "invoice_type")
public class InvoiceType extends Ref {
    @Column(name = "type_name")
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
