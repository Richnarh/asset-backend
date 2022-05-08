package com.khoders.asset.entities.accounting;

import com.khoders.asset.entities.BusinessClient;
import com.khoders.asset.entities.Ref;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "expense")
public class Expense extends Ref {
    @Column(name = "expense_date")
    private LocalDate expenseDate;

    @Column(name = "receipt_no")
    private String receiptNo;

    @JoinColumn(name = "payment_account")
    @ManyToOne
    private Account account;

    @JoinColumn(name = "business_client")
    @ManyToOne
    private BusinessClient businessClient;
}
