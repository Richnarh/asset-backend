package com.khoders.entities;

import com.khoders.entities.constants.AssetStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "asset_register")
public class Asset extends Ref {

    @Column(name = "asset_code")
    private String assetCode;

    @Column(name = "manufacture")
    private String manufacture;

    @Column(name = "install_date")
    private LocalDate installDate;

    @Column(name = "model_number")
    private LocalDate modelNumber;

    @Column(name = "asset_status")
    @Enumerated(EnumType.STRING)
    private AssetStatus assetStatus;

    @Column(name = "website")
    private String website;

    @JoinColumn(name = "inventory_item", referencedColumnName = "id")
    @ManyToOne
    private InventoryItem inventoryItem;

    @Column(name = "description")
    @Lob
    private String description;

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public AssetStatus getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(AssetStatus assetStatus) {
        this.assetStatus = assetStatus;
    }

    public LocalDate getInstallDate() {
        return installDate;
    }

    public void setInstallDate(LocalDate installDate) {
        this.installDate = installDate;
    }

    public LocalDate getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(LocalDate modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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
