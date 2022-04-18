package com.khoders.asset.entities;

import com.khoders.resource.spring.SpringBaseModel;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Ref extends SpringBaseModel {

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
