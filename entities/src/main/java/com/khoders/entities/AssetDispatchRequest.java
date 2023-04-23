package com.khoders.entities;

import com.khoders.entities.constants.ApprovalStatus;

import javax.persistence.*;

@Entity
@Table(name = "asset_dispatch_request")
public class AssetDispatchRequest extends Ref {
    @JoinColumn(name = "department", referencedColumnName = "id")
    @ManyToOne
    private Department department;

    @JoinColumn(name = "received_by")
    @ManyToOne
    private Employee receivedBy;

    @Column(name = "quantity")
    private double quantity;

    @JoinColumn(name = "inventory_item", referencedColumnName = "id")
    @ManyToOne
    private InventoryItem inventoryItem;

    @Column(name = "approval_status")
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    @Column(name = "description")
    @Lob
    private String description;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(Employee receivedBy) {
        this.receivedBy = receivedBy;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
