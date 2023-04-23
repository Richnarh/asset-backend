package com.khoders.asset.dto;

import com.khoders.entities.constants.OtpStatus;

public class PasswordResetResponseDto {
    private OtpStatus otpStatus;
    private String message;

    public PasswordResetResponseDto() {
    }

    public PasswordResetResponseDto(OtpStatus otpStatus, String message) {
        this.otpStatus = otpStatus;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OtpStatus getOtpStatus() {
        return otpStatus;
    }

    public void setOtpStatus(OtpStatus otpStatus) {
        this.otpStatus = otpStatus;
    }
}
