/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.springapi.spring;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author khoders
 */
@MappedSuperclass
public class SpringEntityModel implements Serializable{
    @Column(name = "value_date")
    private LocalDate valueDate;
    
    @Column(name = "date_created")
    private LocalDateTime createdDateTime = LocalDateTime.now();
    
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate; 
    
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    
    @Column(name = "ref_no")
    private String refNo;

    public LocalDateTime getCreatedDateTime()
    {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime)
    {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getLastModifiedDate()
    {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate)
    {
        this.lastModifiedDate = lastModifiedDate;
    }
    
    public String getLastModifiedBy()
    {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy)
    {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getRefNo()
    {
        return refNo;
    }

    public void setRefNo(String refNo)
    {
        this.refNo = refNo;
    }

    public LocalDate getValueDate()
    {
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate)
    {
        this.valueDate = valueDate;
    }

}
