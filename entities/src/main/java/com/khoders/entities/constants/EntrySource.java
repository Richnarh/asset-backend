package com.khoders.entities.constants;

import com.khoders.resource.utilities.MsgResolver;

public enum EntrySource implements MsgResolver {
    BILL("BILL", "Bill"),
    BILL_ITEM("BILL_ITEM", "Bill Item"),
    INVOICE("INVOICE", "Invoice"),
    JOURNAL("JOURNAL", "Journal"),
    EXPENSE("EXPENSE", "Expense");

    private final String code;
    private final String label;

    private EntrySource(String code, String label) {
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
