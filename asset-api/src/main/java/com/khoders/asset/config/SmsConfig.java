package com.khoders.asset.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
public class SmsConfig {
    @Value("${twilio.account_sid}")
    private String accountSid;
    @Value("${twilio.auth_token}")
    private String authToken;
    @Value("${twilio.trial_number}")
    private String trialNumber;
    @Value("${sms.host}")
    private String smsHost;
    @Value("${sms.api_key}")
    private String smsApiKey;
    @Value("${sms.sender}")
    private String SmsSender;

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }
    public String getAuthToken() {
        return authToken;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    public String getTrialNumber() {
        return trialNumber;
    }
    public void setTrialNumber(String trialNumber) {
        this.trialNumber = trialNumber;
    }

    public String getSmsHost() {
        return smsHost;
    }

    public void setSmsHost(String smsHost) {
        this.smsHost = smsHost;
    }

    public String getSmsApiKey() {
        return smsApiKey;
    }

    public void setSmsApiKey(String smsApiKey) {
        this.smsApiKey = smsApiKey;
    }

    public String getSmsSender() {
        return SmsSender;
    }

    public void setSmsSender(String smsSender) {
        SmsSender = smsSender;
    }
}
