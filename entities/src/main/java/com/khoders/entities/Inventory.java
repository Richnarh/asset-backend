package com.khoders.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "inventory")
public class Inventory extends Ref {

    @Column(name = "receipt_no")
    private String receiptNo;

    @Column(name = "received_date")
    private LocalDate receivedDate;

    @JoinColumn(name = "employee", referencedColumnName = "id")
    @ManyToOne
    private Employee receivedBy;

    @JoinColumn(name = "location", referencedColumnName = "id")
    @ManyToOne
    private Location receivedAt;

    @JoinColumn(name = "business_client", referencedColumnName = "id")
    @ManyToOne
    private BusinessClient businessClient;

    @Column(name = "total_payable")
    private double totalPayable;

    @Transient
    private List<InventoryItem> inventoryItemList = new LinkedList<>();

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Employee getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(Employee receivedBy) {
        this.receivedBy = receivedBy;
    }

    public Location getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Location receivedAt) {
        this.receivedAt = receivedAt;
    }


    public double getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable) {
        this.totalPayable = totalPayable;
    }

    public BusinessClient getBusinessClient() {
        return businessClient;
    }

    public void setBusinessClient(BusinessClient businessClient) {
        this.businessClient = businessClient;
    }

    public List<InventoryItem> getInventoryItemList() {
        return inventoryItemList;
    }

    public void setInventoryItemList(List<InventoryItem> inventoryItemList) {
        this.inventoryItemList = inventoryItemList;
    }
}
