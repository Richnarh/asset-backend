package com.khoders.asset.dto;

public class InventoryDto extends BaseDto {
    private String receiptNo;
    private String receivedDate;
    private String receivedByName; // employeeName
    private String receivedById; // employeeId
    private String receivedAt; // locationName
    private String receivedAtId; // locationId
    private String vendorName;
    private String vendorId;
    private double totalPayable;

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getReceivedByName() {
        return receivedByName;
    }

    public void setReceivedByName(String receivedByName) {
        this.receivedByName = receivedByName;
    }

    public String getReceivedById() {
        return receivedById;
    }

    public void setReceivedById(String receivedById) {
        this.receivedById = receivedById;
    }

    public String getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(String receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getReceivedAtId() {
        return receivedAtId;
    }

    public void setReceivedAtId(String receivedAtId) {
        this.receivedAtId = receivedAtId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public double getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable) {
        this.totalPayable = totalPayable;
    }
}
