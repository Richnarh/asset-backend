package com.khoders.asset.controller.auth;

import com.khoders.asset.dto.PasswordResetRequestDto;
import com.khoders.asset.dto.PasswordResetResponseDto;
import com.khoders.asset.services.auth.SmsService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Tag(name = "SMS - Endpoint")
@RequestMapping(ApiEndpoint.SMS_ENDPOINT)
public class SmsController {
    @Autowired
    private SmsService otpService;

    @PostMapping(value = "/otp")
    public ResponseEntity<PasswordResetResponseDto> sendOTP(@Valid @RequestBody PasswordResetRequestDto resetRequest){
        PasswordResetResponseDto otpResponse = otpService.sendOtp(resetRequest);
        return ApiResponse.ok(otpResponse);
    }
    @PostMapping(value = "/validate")
    public ResponseEntity<String> validateOTP(@Valid @RequestBody PasswordResetRequestDto resetRequest){
        return ApiResponse.ok("Validate OTP", otpService.validateOtp(resetRequest.getOneTimePassword(), resetRequest.getUsername()));
    }
    @PostMapping(value = "/msg")
    public ResponseEntity<String> sendMessage(@Valid @RequestBody String phoneNumber) {
        String optMsg = otpService.genOtp(phoneNumber);
        if( optMsg != null){
            return ApiResponse.ok("OTP Message", optMsg);
        }
         return ApiResponse.error("OTP Message", "Could not send message");
    }
}
