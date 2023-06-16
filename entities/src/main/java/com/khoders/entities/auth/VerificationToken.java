package com.khoders.entities.auth;

import com.khoders.springapi.spring.SpringBaseModel;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity(name = "verification_token")
public class VerificationToken extends SpringBaseModel {
    public static final String _token = "token";
    @Column(name = "token")
    private String token;
    @Column(name = "expiration_time")
    private Date expirationTime;
    @Transient
    private static final int EXPIRATION_TIME = 15;

    public static final String _userAccountId = UserAccount._id;
    @JoinColumn(name = "user_account", referencedColumnName = "id")
    @OneToOne
    private UserAccount userAccount;

    public VerificationToken() {
    }

    public VerificationToken(String token, UserAccount userAccount) {
        super();
        this.token = token;
        this.userAccount = userAccount;
        this.expirationTime = this.getTokenExpirationTime();
    }

    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime = this.getTokenExpirationTime();
    }

    private Date getTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public Date getExpirationTime() {
        return expirationTime;
    }
    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }
    public UserAccount getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
