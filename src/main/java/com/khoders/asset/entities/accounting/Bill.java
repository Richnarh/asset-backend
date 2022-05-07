package com.khoders.asset.entities.accounting;

import com.khoders.asset.entities.BusinessClient;
import com.khoders.asset.entities.Ref;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bills")
public class Bill extends Ref {
    @JoinColumn(name = "business_client", referencedColumnName = "id")
    @ManyToOne
    private BusinessClient businessClient;

    @Column(name = "receipt_no")
    private String receiptNo;

    @Column(name = "receipt_date")
    private LocalDate receiptDate;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "memo")
    @Lob
    private String memo;
}
