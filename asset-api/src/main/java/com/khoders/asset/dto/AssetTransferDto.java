package com.khoders.asset.dto;

public class AssetTransferDto extends BaseDto {
    private String transferFrom;
    private String transferFromId;
    private String transferTo;
    private String transferToId;
    private String transferDate;
    private String description;

    public String getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(String transferFrom) {
        this.transferFrom = transferFrom;
    }

    public String getTransferFromId() {
        return transferFromId;
    }

    public void setTransferFromId(String transferFromId) {
        this.transferFromId = transferFromId;
    }

    public String getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(String transferTo) {
        this.transferTo = transferTo;
    }

    public String getTransferToId() {
        return transferToId;
    }

    public void setTransferToId(String transferToId) {
        this.transferToId = transferToId;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
