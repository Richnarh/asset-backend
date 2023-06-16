package com.khoders.asset.dto.accounting;

public class BillItemDto extends BillInvoiceMappedClass {
    private String bill;
    private String billId;

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }
}
