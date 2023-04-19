package com.khoders.asset.dto;

public class Sql {
    public static final String EXPENSE_ITEM_BY_EXPENSE_ID = "SELECT * FROM expense_item WHERE expense =:id";
    public static final String BILL_ITEM_BY_BILL_ID = "SELECT * FROM bill_item WHERE bills =:id";
    public static final String PAYMENT_BY_BILL_ID = "SELECT * FROM bill_item WHERE bills =:id";
    public static final String PAYMENT_BY_INVOICE_ID = "SELECT * FROM bill_item WHERE invoice =:id";
    public static final String INSTRUCTION_SET_BY_ID = "SELECT * FROM bill_item WHERE invoice =:id";
    public static final String INVENTORY_ITEM_INV_ID = "SELECT * FROM inventory_item WHERE inventory =:id";
    public static final String INVOICE_ITEM_BY_INVOICE_ID = "SELECT * FROM invoice_item WHERE invoice =:id";
    public static final String COMPANY_BY_USER_ID = "SELECT * FROM company WHERE primary_user =:id ORDER BY company_name ASC";
    public static final String COMPANY_BY_EMAIL = "SELECT * FROM company WHERE company_address =:companyAddress";
}
