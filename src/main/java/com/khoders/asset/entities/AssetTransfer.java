package com.khoders.asset.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "asset_transfer")
public class AssetTransfer extends Ref{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "varchar(255)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(name = "transfer_from")
    private Location transferFrom;

    @Column(name = "transfer_to")
    private Location transferTo;

    @Column(name = "transfer_date")
    private Location transferDate;

    @Column(name = "description")
    @Lob
    private Location description;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Location getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(Location transferFrom) {
        this.transferFrom = transferFrom;
    }

    public Location getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(Location transferTo) {
        this.transferTo = transferTo;
    }

    public Location getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Location transferDate) {
        this.transferDate = transferDate;
    }

    public Location getDescription() {
        return description;
    }

    public void setDescription(Location description) {
        this.description = description;
    }
}
