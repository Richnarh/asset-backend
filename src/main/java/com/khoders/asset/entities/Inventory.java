package com.khoders.asset.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "inventory")
public class Inventory extends Ref{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "varchar(255)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(name = "receipt_no")
    private String receiptNo;

    @Column(name = "received_date")
    private LocalDate receivedDate;

    @JoinColumn(name = "received_by")
    @ManyToOne
    private Employee receivedBy;

    @JoinColumn(name = "received_at")
    @ManyToOne
    private Location receivedAt;

    @JoinColumn(name = "vendor", referencedColumnName = "id")
    @ManyToOne
    private Vendor vendor;

    @Column(name = "total_payable")
    private double totalPayable;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public double getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable) {
        this.totalPayable = totalPayable;
    }
}
