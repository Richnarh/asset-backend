package com.khoders.asset.advice;

import com.khoders.asset.exceptions.AppError;
import com.khoders.asset.exceptions.BadDataException;
import com.khoders.springapi.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class BadDataExceptionHandler {
    @ExceptionHandler(BadDataException.class)
    public ResponseEntity<AppError> handleException(BadDataException ex) {
        AppError error = new AppError(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage());
        return ApiResponse.error(error, HttpStatus.BAD_REQUEST);
    }
}
