package com.khoders.asset.entities;

import com.khoders.resource.enums.ClientType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "business_client")
public class BusinessClient extends Ref{
    @Column(name = "client_type")
    private ClientType clientType;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "address")
    private String address;

    @Column(name = "zipcode")
    private String zipCode;

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
