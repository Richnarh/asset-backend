package com.khoders.asset.entities;

import com.khoders.asset.entities.constants.AssetStatus;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "asset_register")
public class AssetRegister extends Ref{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "varchar(255)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(name = "asset_code")
    private String assetCode;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "asset_status")
    @Enumerated(EnumType.STRING)
    private AssetStatus assetStatus;

    @Column(name = "lifespan")
    private String lifeSpan;

    @JoinColumn(name = "inventory_item", referencedColumnName = "id")
    @ManyToOne
    private InventoryItem inventoryItem;

    @Column(name = "description")
    @Lob
    private String description;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public AssetStatus getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(AssetStatus assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
