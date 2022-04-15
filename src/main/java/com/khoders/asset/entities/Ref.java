package com.khoders.asset.entities;

import com.khoders.resource.jpa.EntityModel;
import com.khoders.resource.utilities.SystemUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Ref extends EntityModel {
    @Column(name = "ref_no")
    private String refNo;

    @JoinColumn(name = "company")
    @ManyToOne
    private Company company;

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void genRef()
    {
        if (getRefNo() != null)
        {
            setRefNo(getRefNo());
        } else
        {
            setRefNo(SystemUtils.generateRefNo());
        }
    }
}
