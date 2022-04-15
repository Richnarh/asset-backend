package com.khoders.asset.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "inventory_item")
public class InventoryItem extends Ref{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "varchar(255)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(name = "product_name")
    private String productName;

    @JoinColumn(name = "category", referencedColumnName = "id")
    @ManyToOne
    private Category productCategory;

    @JoinColumn(name = "inventory", referencedColumnName = "id")
    @ManyToOne
    private Inventory inventory;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "total_amount")
    private double totalAmount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return productName + " "+ serialNumber ;
    }
}
