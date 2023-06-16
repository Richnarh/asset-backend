package com.khoders.entities.accounting;

import com.khoders.entities.Ref;
import com.khoders.entities.constants.AccountType;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
public class Account extends Ref {
    @Column(name = "account_name")
    private String accountName;

    @Column(name = "parent_account")
    private String parentAccount;

    @Column(name = "account_code")
    private String accountCode;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "sub_account")
    private boolean subAccount;

    @Column(name = "description")
    @Lob
    private String description;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getParentAccount() {
        return parentAccount;
    }

    public void setParentAccount(String parentAccount) {
        this.parentAccount = parentAccount;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isSubAccount() {
        return subAccount;
    }

    public void setSubAccount(boolean subAccount) {
        this.subAccount = subAccount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return accountName;
    }

}
