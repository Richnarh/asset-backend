package com.khoders.asset.services.auth;

import Zenoph.Notify.Enums.AuthModel;
import Zenoph.Notify.Enums.TextMessageType;
import Zenoph.Notify.Request.SMSRequest;
import com.khoders.asset.config.SmsConfig;
import com.khoders.asset.dto.PasswordResetRequestDto;
import com.khoders.asset.dto.PasswordResetResponseDto;
import com.khoders.asset.exceptions.BadDataException;
import com.khoders.entities.constants.OtpStatus;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class SmsService {
    private static final Logger log = LoggerFactory.getLogger(SmsService.class);
    @Autowired private SmsConfig smsConfig;
    Map<String,String> otpMap = new HashMap<>();
    public PasswordResetResponseDto sendOtp(PasswordResetRequestDto requestDto){
        PasswordResetResponseDto responseDto = null;

        try {
            PhoneNumber to = new PhoneNumber(requestDto.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(smsConfig.getTrialNumber());
            String otp = generateOtp();
            String otpMessage = "Dear Customer, Your OTP is ## "+otp+" ##. Use this passcode to complete your transaction. Thank you.";

            Message message = Message.creator(to,from,otpMessage).create();

            responseDto = new PasswordResetResponseDto(OtpStatus.DELIVERED, otpMessage);
            otpMap.put(requestDto.getUsername(), otp);
        }catch (Exception e){
            responseDto = new PasswordResetResponseDto(OtpStatus.FAILED, e.getMessage());
        }
        return responseDto;
    }

    public String validateOtp(String userInputOtp, String username){
        if(userInputOtp.equals(otpMap.get(username))){
            otpMap.remove(username,userInputOtp);
            return new String("Valid OTP please proceed with your transaction!");
        }else {
            return String.valueOf(new IllegalArgumentException("Invalid exception, please retry !"));
        }
    }
    private String generateOtp(){
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }

    public void sendSms(String message, String phoneNumber){
        try{
            SMSRequest sr = initRequest();
            if(sr != null){
                sr.setMessage(message);
                sr.addDestination(phoneNumber);
                sr.submit();
            }else {
                throw new BadDataException("Could not initialise sms request!");
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
    public String genOtp(String phoneNumber){
        String otpMessage = "Dear Customer, Your OTP is: "+generateOtp()+". Use this passcode to complete your transaction. Thank you.";
        try{
            SMSRequest sr = initRequest();
            if(sr != null){
                sr.setMessage(otpMessage);
                sr.addDestination(phoneNumber);
                sr.submit();
                return "Sms sent!";
            }else {
                throw new BadDataException("Could not initialise sms request!");
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    public SMSRequest initRequest(){
        try {
            SMSRequest sr = new SMSRequest();
            sr.setAuthModel(AuthModel.API_KEY);
            sr.setAuthApiKey(smsConfig.getSmsApiKey());
            sr.setMessageType(TextMessageType.FLASH_TEXT);
            sr.setSender(smsConfig.getSmsSender());
            return sr;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
