package com.khoders.asset.config;

import java.util.ArrayList;

public class Cors {
    private static final ArrayList<String> ALLOWED_HEADERS = new ArrayList<>();
    private static final ArrayList<String> ALLOWED_ORIGIN = new ArrayList<>();
    private static final ArrayList<String> ALLOWED_METHODS = new ArrayList<>();

    public static ArrayList<String> headers() {
        ALLOWED_HEADERS.add("Origin");
        ALLOWED_HEADERS.add("Access-Control-Allow-Origin");
        ALLOWED_HEADERS.add("Content-Type");
        ALLOWED_HEADERS.add("Authorization");
        ALLOWED_HEADERS.add("companyId");
        ALLOWED_HEADERS.add("Origin,Accept");
        ALLOWED_HEADERS.add("X-Requested-With");
        ALLOWED_HEADERS.add("Access-Control-Request-Method");
        ALLOWED_HEADERS.add("Access-Control-Request-Headers");
        return ALLOWED_HEADERS;
    }

    public static ArrayList<String> origins() {
        ALLOWED_ORIGIN.add("http://localhost:2500");
        return ALLOWED_ORIGIN;
    }

    public static ArrayList<String> methods() {
        ALLOWED_METHODS.add("GET");
        ALLOWED_METHODS.add("POST");
        ALLOWED_METHODS.add("DELETE");
        ALLOWED_METHODS.add("OPTIONS");
        return ALLOWED_METHODS;
    }
}
