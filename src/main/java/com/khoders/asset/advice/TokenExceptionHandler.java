package com.khoders.asset.advice;

import com.khoders.asset.exceptions.AppError;
import com.khoders.asset.exceptions.TokenRefreshException;
import com.khoders.springapi.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class TokenExceptionHandler {
    @ExceptionHandler(value = TokenRefreshException.class)
    public ResponseEntity<AppError> handleTokenRefreshException(TokenRefreshException ex) {
        AppError error = new AppError(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage());

        return ApiResponse.error(error, HttpStatus.FORBIDDEN);
    }
}
