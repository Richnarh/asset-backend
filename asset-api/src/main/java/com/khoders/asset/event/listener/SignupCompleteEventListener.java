package com.khoders.asset.event.listener;

import com.khoders.asset.event.SignupCompleteEvent;
import com.khoders.asset.services.auth.AuthService;
import com.khoders.entities.auth.UserAccount;
import com.khoders.springapi.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SignupCompleteEventListener implements ApplicationListener<SignupCompleteEvent> {
    private static final Logger log = LoggerFactory.getLogger(SignupCompleteEventListener.class);
    @Autowired private AppService appService;
    @Autowired private AuthService authService;

    @Override
    public void onApplicationEvent(SignupCompleteEvent event) {
        UserAccount userAccount = event.getUserAccount();
        String verificationToken = appService.genId();
        authService.saveUserVerificationToken(userAccount,verificationToken);
        String url = event.getApplicationUrl()+"/auth/verify-email?token="+verificationToken;
        System.out.println("Verification URL: "+url);
        log.info("Click the link to verify your registration: {}",url);
    }
}
