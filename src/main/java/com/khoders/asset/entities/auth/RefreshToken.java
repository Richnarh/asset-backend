package com.khoders.asset.entities.auth;

import com.khoders.asset.entities.Ref;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "refreshtoken")
public class RefreshToken extends Ref {
    @JoinColumn(name = "user_account", referencedColumnName = "id")
    @OneToOne
    private UserAccount userAccount;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
}
