package com.khoders.asset.entities.accounting;

import com.khoders.asset.entities.Ref;

import javax.persistence.*;

@Entity
@Table(name = "bank_transaction")
public class BankTransaction extends Ref {
    @JoinColumn(name = "account", referencedColumnName = "id")
    @ManyToOne
    private Account account;

    @Column(name = "amount")
    private double amount;

}
