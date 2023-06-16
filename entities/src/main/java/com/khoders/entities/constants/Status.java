package com.khoders.entities.constants;

import com.khoders.resource.utilities.MsgResolver;

public enum Status implements MsgResolver {
    OPEN("OPEN", "Open"),
    PENDING("PENDING", "Pending"),
    IN_PROGRESS("IN_PROGRESS", "In Progress"),
    CLOSE("CLOSE", "Close");

    private final String code;
    private final String label;

    Status(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
