package com.khoders.asset.exceptions;

import java.util.Date;

public class AppError {
    private int errCode;
    private Date timestamp;
    private String message;

    public AppError(Integer errCode, Date timestamp, String message) {
        this.errCode = errCode;
        this.timestamp = timestamp;
        this.message = message;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
