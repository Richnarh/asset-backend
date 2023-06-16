package com.khoders.entities.constants;

import com.khoders.resource.utilities.MsgResolver;

public enum CompanyType implements MsgResolver {
    PARENT_COMPANY("PARENT_COMPANY", "Parent Company"),
    CHILD_COMPANY("CHILD_COMPANY", "Child Company");

    private final String code;
    private final String label;

    private CompanyType(String code, String label) {
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
