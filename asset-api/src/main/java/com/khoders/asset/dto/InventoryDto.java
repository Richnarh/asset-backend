package com.khoders.asset.dto;

import java.util.LinkedList;
import java.util.List;

public class InventoryDto extends BaseDto {
    private String receiptNo;
    private String receivedDate;
    private String receivedByName; // employeeName
    private String receivedById; // employeeId
    private String receivedAt; // locationName
    private String receivedAtId; // locationId
    private String businessClientName;
    private String businessClientId;
    private double totalPayable;
    List<InventoryItemDto> inventoryItemList = new LinkedList<>();

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

    public String getBusinessClientName() {
        return businessClientName;
    }

    public void setBusinessClientName(String businessClientName) {
        this.businessClientName = businessClientName;
    }

    public String getBusinessClientId() {
        return businessClientId;
    }

    public void setBusinessClientId(String businessClientId) {
        this.businessClientId = businessClientId;
    }

    public double getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable) {
        this.totalPayable = totalPayable;
    }

    public List<InventoryItemDto> getInventoryItemList() {
        return inventoryItemList;
    }

    public void setInventoryItemList(List<InventoryItemDto> inventoryItemList) {
        this.inventoryItemList = inventoryItemList;
    }
}
