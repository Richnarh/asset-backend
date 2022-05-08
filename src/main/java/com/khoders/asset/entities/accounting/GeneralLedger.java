package com.khoders.asset.entities.accounting;

import com.khoders.asset.entities.Ref;
import com.khoders.asset.entities.constants.EntrySource;

import javax.persistence.*;

@Entity
@Table(name = "general_ledger")
public class GeneralLedger extends Ref {
    @JoinColumn(name = "account", referencedColumnName = "id")
    @ManyToOne
    private Account account;

    @Column(name = "debit")
    private double debit;

    @Column(name = "credit")
    private double credit;

    @Column(name = "entry_source")
    @Enumerated(EnumType.STRING)
    private EntrySource entrySource;

    @Column(name = "description")
    @Lob
    private String description;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public EntrySource getEntrySource() {
        return entrySource;
    }

    public void setEntrySource(EntrySource entrySource) {
        this.entrySource = entrySource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
