package com.khoders.asset.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

public class SpringUtils {

    public static UUID stringToUUID(String stringName) {
        return UUID.fromString(stringName);
    }

    public static String UUIDtoString(UUID uuid) {
        return uuid.toString();
    }

    public static Gson KJson() {
        GsonBuilder builder = new GsonBuilder();
        return builder.setPrettyPrinting().create();
    }


}
