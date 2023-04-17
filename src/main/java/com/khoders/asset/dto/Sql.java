package com.khoders.asset.dto;

public class Sql {
	public static final String EXPENSE_ITEM_BY_EXPENSEID = "SELECT * FROM expense_item WHERE expense =:id";
	public static final String BILL_ITEM_BY_BILLID = "SELECT * FROM bill_item WHERE bills =:id";
	public static final String PAYMENT_BY_BILLID = "SELECT * FROM bill_item WHERE bills =:id";
	public static final String PAYMENT_BY_INVOICEID = "SELECT * FROM bill_item WHERE invoice =:id";
	public static final String INSTRUCTIONSET_BY_ID = "SELECT * FROM bill_item WHERE invoice =:id";
	public static final String INVENTORYITEM_INV_ID = "SELECT * FROM inventory_item WHERE inventory =:id";
	public static final String INVOICEITEM_BY_INVOICE_ID = "SELECT * FROM invoice_item WHERE invoice =:id";
	public static final String COMPANY_BY_USERID = "SELECT * FROM company WHERE primary_user =:id ORDER BY company_name ASC";
	public static final String COMPANY_BY_EMAIL = "SELECT * FROM company WHERE company_address =:companyAddress";
}
