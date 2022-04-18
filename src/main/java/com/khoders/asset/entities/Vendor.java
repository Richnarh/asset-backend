package com.khoders.asset.entities;

import com.khoders.resource.enums.ClientType;

import javax.persistence.*;

@Entity
@Table(name = "vendor")
public class Vendor extends Ref {

    @Column(name = "vendor_type")
    @Enumerated(EnumType.STRING)
    private ClientType vendorType;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "phone_number")
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
