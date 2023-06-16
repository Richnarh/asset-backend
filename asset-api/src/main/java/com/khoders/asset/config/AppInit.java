package com.khoders.asset.config;

import Zenoph.Notify.Request.SMSRequest;
import com.twilio.Twilio;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@PropertySource("classpath:config.properties")
public class AppInit {
    private static final Logger log = getLogger(AppInit.class);
    @Autowired
    private SmsConfig smsConfig;

    @PostConstruct
    public void init(){
        Twilio.init(smsConfig.getAccountSid(),smsConfig.getAuthToken());
        try{
            SMSRequest.setHost(smsConfig.getSmsHost());
        }catch (Exception e){
            log.error("error: {} ", e.getMessage());
        }
    }
}
