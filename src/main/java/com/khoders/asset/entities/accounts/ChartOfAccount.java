package com.khoders.asset.entities.accounts;

import com.khoders.asset.entities.Ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "chart_of_account")
public class ChartOfAccount extends Ref {
    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_type")
    private String accountType;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return accountName;
    }
}