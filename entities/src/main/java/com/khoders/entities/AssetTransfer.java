package com.khoders.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "asset_transfer")
public class AssetTransfer extends Ref {

    @JoinColumn(name = "transfer_from", referencedColumnName = "id")
    @ManyToOne
    private Location transferFrom;

    @JoinColumn(name = "transfer_to", referencedColumnName = "id")
    @ManyToOne
    private Location transferTo;

    @Column(name = "transfer_date")
    private LocalDate transferDate;

    @Column(name = "description")
    @Lob
    private String description;

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

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
