package com.khoders.entities;

import com.khoders.entities.auth.UserAccount;
import com.khoders.entities.constants.CompanyType;
import com.khoders.springapi.spring.SpringBaseModel;

import javax.persistence.*;

@Entity
@Table(name = "company")
public class Company extends SpringBaseModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final String _companyName = "companyName";
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

    public static final String _companyAddress = "companyAddress";
    @Column(name = "company_address")
    private String companyAddress;

    public static final String _primaryUser = "primaryUser";
    public static final String _primaryUserId = UserAccount._id;
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
