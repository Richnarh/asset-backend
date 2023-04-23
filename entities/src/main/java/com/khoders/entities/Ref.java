package com.khoders.entities;

import com.khoders.entities.auth.UserAccount;
import com.khoders.springapi.spring.SpringBaseModel;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Ref extends SpringBaseModel {

    @JoinColumn(name = "company")
    @ManyToOne
    private Company company;

    @JoinColumn(name = "user_account")
    @ManyToOne
    private UserAccount userAccount;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
