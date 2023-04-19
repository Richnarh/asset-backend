package com.khoders.asset.dto.accounting;

public class ExpenseItemDto extends BillInvoiceMappedClass {
    private String expense;
    private String expenseId;

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }
}
