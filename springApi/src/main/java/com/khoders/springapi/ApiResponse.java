package com.khoders.springapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class ApiResponse {
    public static <T> ResponseEntity<T> ok(String message, Object responseObj) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", message);
        map.put("success", true);
        map.put("statusCode", HttpStatus.OK.value());
        map.put("data", responseObj);

        return new ResponseEntity<>((T) map, HttpStatus.OK);
    }
    public static <T> ResponseEntity<T> ok(Object responseObj) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("success", true);
        map.put("statusCode", HttpStatus.OK.value());
        map.put("data", responseObj);

        return new ResponseEntity<>((T) map, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> created(String message, Object responseObj) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", message);
        map.put("success", true);
        map.put("statusCode", HttpStatus.CREATED.value());
        map.put("data", responseObj);

        return new ResponseEntity<>((T) map, HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<T> created(Object responseObj) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", "Record Created Successfully!");
        map.put("success", true);
        map.put("statusCode", HttpStatus.CREATED.value());
        map.put("data", responseObj);

        return new ResponseEntity<>((T) map, HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<T> notFound(String message, Object responseObj) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", message);
        map.put("success", false);
        map.put("statusCode", HttpStatus.NOT_FOUND.value());
        map.put("data", responseObj);

        return new ResponseEntity<>((T) map, HttpStatus.NOT_FOUND);
    }

    public static <T> ResponseEntity<T> notFound(String message) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", message);
        map.put("success", false);
        map.put("statusCode", HttpStatus.NOT_FOUND.value());
        
        return new ResponseEntity<>((T) map, HttpStatus.NOT_FOUND);
    }

    public static <T> ResponseEntity<T> notFound() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", "No Record Found");
        map.put("success", false);
        map.put("statusCode", HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>((T) map, HttpStatus.NOT_FOUND);
    }

    public static <T> ResponseEntity<T> error(String message, Object responseObj) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", message);
        map.put("success", false);
        map.put("statusCode", HttpStatus.BAD_REQUEST.value());
        map.put("data", responseObj);

        return new ResponseEntity<>((T) map, HttpStatus.BAD_REQUEST);
    }
    
    public static <T> ResponseEntity<T> error(Object responseObj, HttpStatus status) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("data", responseObj);
        map.put("statusCode", status.value());
        return new ResponseEntity<>((T) map, status);
    }
}
