package com.khoders.asset.services;

import com.khoders.entities.Company;
import com.khoders.springapi.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private static final Logger log = LoggerFactory.getLogger(CompanyService.class);
    @Autowired
    private AppService appService;

    public Company saveCompany(Company company) {
        return appService.save(company);
    }

    public List<Company> companyList() {
        return appService.findAll(Company.class);
    }

    public Company findById(String companyId) {
        return appService.findById(Company.class, companyId);
    }

    public boolean delete(String companyId) throws Exception {
        return appService.deleteById(Company.class, companyId);
    }
}
