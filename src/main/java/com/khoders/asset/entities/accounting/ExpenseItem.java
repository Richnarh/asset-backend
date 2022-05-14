package com.khoders.asset.entities.accounting;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "expense_item")
public class ExpenseItem extends BillInvoice{
    public static final String _expense = "expense";
    @JoinColumn(name = "expense", referencedColumnName = "id")
    @ManyToOne
    private Expense expense;

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }
}
