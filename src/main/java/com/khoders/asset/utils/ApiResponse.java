package com.khoders.asset.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse {
    public static <T> ResponseEntity<T> ok(String message, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("success", true);
        map.put("data", responseObj);

        return new ResponseEntity<>((T) map, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> created(String message, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("success", true);
        map.put("data", responseObj);

        return new ResponseEntity<>((T) map, HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<T> notFound(String message, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("success", false);
        map.put("data", responseObj);

        return new ResponseEntity<>((T) map, HttpStatus.NOT_FOUND);
    }

    public static <T> ResponseEntity<T> error(String message, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("success", false);
        map.put("data", responseObj);

        return new ResponseEntity<>((T) map, HttpStatus.BAD_REQUEST);
    }
}
