package com.khoders.entities.auth;

import com.khoders.entities.Ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "refreshtoken")
public class RefreshToken extends Ref {
    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false, name = "issued_at")
    private Date issuedAt;

    @Column(nullable = false, name = "expiry_date")
    private Date expiryDate;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
