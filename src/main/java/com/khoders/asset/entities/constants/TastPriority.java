package com.khoders.asset.entities.constants;

import com.khoders.resource.utilities.MsgResolver;

public enum TastPriority implements MsgResolver {
    HIGHEST("HIGHEST","Highest"),
    HIGH("HIGH", "High"),
    MEDIUM("MEDIUM", "Medium"),
    LOW("LOW", "Low");

    private final String code;
    private final String label;

    TastPriority(String code, String label){
        this.code=code;
        this.label=label;
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
