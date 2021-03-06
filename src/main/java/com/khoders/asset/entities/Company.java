package com.khoders.asset.entities;

import com.khoders.asset.entities.auth.UserAccount;
import com.khoders.asset.entities.constants.CompanyType;
import com.khoders.resource.spring.SpringBaseModel;

import javax.persistence.*;

@Entity
@Table(name = "company")
public class Company extends SpringBaseModel {

    public static final String _companyName="companyName";
    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_type")
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "website")
    private String website;

    @Column(name = "zipcode")
    private String zipCode;

    @Column(name = "company_address")
    private String companyAddress;

    public static final String _primaryUser="primaryUser";
    @JoinColumn(name = "primary_user", referencedColumnName = "id")
    @ManyToOne
    private UserAccount primaryUser;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public UserAccount getPrimaryUser() {
        return primaryUser;
    }

    public void setPrimaryUser(UserAccount primaryUser) {
        this.primaryUser = primaryUser;
    }
}
