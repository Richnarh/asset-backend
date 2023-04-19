package com.khoders.asset.dto.accounting;

public class InvoiceItemDto extends BillInvoiceMappedClass {
    private String invoice;
    private String invoiceId;

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
}
