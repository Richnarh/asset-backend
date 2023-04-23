package com.khoders.entities.settings;

import com.khoders.entities.Ref;

import javax.persistence.*;

@Entity
@Table(name = "invoice_footer")
public class InvoiceFooter extends Ref {
    @JoinColumn(name = "invoice_type", referencedColumnName = "id")
    @ManyToOne
    private InvoiceType invoiceType;

    @Column(name = "footer_content")
    @Lob
    private String footerContent;

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getFooterContent() {
        return footerContent;
    }

    public void setFooterContent(String footerContent) {
        this.footerContent = footerContent;
    }
}
