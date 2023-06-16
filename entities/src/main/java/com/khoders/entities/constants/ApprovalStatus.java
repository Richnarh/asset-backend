package com.khoders.entities.constants;

import com.khoders.resource.utilities.MsgResolver;

public enum ApprovalStatus implements MsgResolver {
    PENDING("PENDING", "Pending"),
    ACCEPTED("ACCEPTED", "Accepted"),
    REJECTED("REJECTED", "Rejected");

    private final String code;
    private final String label;

    private ApprovalStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
