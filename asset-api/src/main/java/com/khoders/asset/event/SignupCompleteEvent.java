package com.khoders.asset.event;

import com.khoders.entities.auth.UserAccount;
import org.springframework.context.ApplicationEvent;

public class SignupCompleteEvent extends ApplicationEvent {
    private UserAccount userAccount;
    private String applicationUrl;
    public SignupCompleteEvent(UserAccount userAccount, String applicationUrl) {
        super(userAccount);
        this.userAccount = userAccount;
        this.applicationUrl = applicationUrl;
    }
    public UserAccount getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
    public String getApplicationUrl() {
        return applicationUrl;
    }
    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }
}
