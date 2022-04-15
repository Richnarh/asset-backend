package com.khoders.asset.entities;

import com.khoders.resource.jpa.EntityModel;
import com.khoders.resource.utilities.SystemUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public class Ref extends EntityModel {

    @Column(name = "ref_no")
    private String refNo;
    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
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
