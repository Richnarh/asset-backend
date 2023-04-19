package com.khoders.asset.advice;

import com.khoders.asset.exceptions.AppError;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.springapi.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class DataNotFoundExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<AppError> handleException(DataNotFoundException ex) {
        AppError error = new AppError(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage());
        return ApiResponse.error(error, HttpStatus.NOT_FOUND);
    }
}
