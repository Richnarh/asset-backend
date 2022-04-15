package com.khoders.asset.dto;

import com.khoders.resource.enums.ClientType;

public class VendorDto extends BaseDto {
    private ClientType vendorType;
    private String vendorName;
    private String emailAddress;
    private String phoneNumber;

    public ClientType getVendorType() {
        return vendorType;
    }

    public void setVendorType(ClientType vendorType) {
        this.vendorType = vendorType;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
