package com.khoders.asset.entities.constants;

import com.khoders.asset.utils.EnumConverter;

public enum ApprovalStatus implements Enums<String> {
    PENDING("PENDING", "Pending"), ACCEPTED("ACCEPTED", "Accepted"), REJECTED("REJECTED", "Rejected");

    private final String code;
    private final String label;

    private ApprovalStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String getValue() {
        return label;
    }
    public static class Converter extends EnumConverter<ApprovalStatus, String> {
        public Converter() {
            super(ApprovalStatus.class);
        }
    }
}
