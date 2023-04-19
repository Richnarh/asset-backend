package com.khoders.asset.advice;

import com.khoders.asset.exceptions.AppError;
import com.khoders.asset.exceptions.InternalErrException;
import com.khoders.springapi.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class InternalErrExceptionHandler {
    @ExceptionHandler(InternalErrException.class)
    public ResponseEntity<AppError> handleException(InternalErrException ex) {
        AppError error = new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage());
        return ApiResponse.error(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
