package com.khoders.entities.constants;

import com.khoders.resource.utilities.MsgResolver;

public enum DebitCredit implements MsgResolver {
    DEBIT("DEBIT", "Debit"),
    CREDIT("CREDIT", "Credit");

    private final String code;
    private final String label;

    private DebitCredit(String code, String label) {
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
