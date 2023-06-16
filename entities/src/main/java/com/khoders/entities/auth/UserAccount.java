package com.khoders.entities.auth;


import com.khoders.entities.CompanyRecord;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_account")
public class UserAccount extends CompanyRecord {
    public static final String _emailAddress = "emailAddress";
    @Column(name = "email_address")
    private String emailAddress;

    public static final String _primaryNumber = "primaryNumber";
    @Column(name = "primary_number")
    private String primaryNumber;

    public static final String _password = "password";
    @Column(name = "password")
    private String password;
    @Column(name = "is_enabled")
    private boolean enabled;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "roles",referencedColumnName = "id")
    private Set<Role> roles;
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getPrimaryNumber() {
        return primaryNumber;
    }
    public void setPrimaryNumber(String primaryNumber) {
        this.primaryNumber = primaryNumber;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return emailAddress;
    }
}
