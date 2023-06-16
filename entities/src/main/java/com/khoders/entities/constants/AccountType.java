package com.khoders.entities.constants;

import com.khoders.resource.utilities.MsgResolver;

public enum AccountType implements MsgResolver {
    ASSETS("ASSETS", "Assets"),
    LIABILITY("LIABILITY", "Liability"),
    EXPENSE("EXPENSE", "Expense");

    private final String code;
    private final String label;

    private AccountType(String code, String label) {
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
