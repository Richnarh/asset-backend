package com.khoders.entities.constants;

import com.khoders.resource.utilities.MsgResolver;

public enum AssetStatus implements MsgResolver {
    NEW("NEW", "New"), OLD("OLD", "Old"), USED("USED", "Used"), DAMAGED("DAMAGED", "Damaged");

    private final String label;
    private final String code;

    private AssetStatus(String code, String label) {
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
