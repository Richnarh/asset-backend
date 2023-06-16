package com.khoders.entities;


import com.khoders.springapi.spring.SpringBaseModel;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class CompanyRecord extends SpringBaseModel {
    public static final String _company = "company";
    public static final String _company_id = _company + "." + Company._id;
    @JoinColumn(name = "company")
    @ManyToOne
    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
