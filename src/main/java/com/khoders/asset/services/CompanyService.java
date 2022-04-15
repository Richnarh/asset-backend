package com.khoders.asset.services;

import com.khoders.asset.entities.Category;
import com.khoders.asset.entities.Company;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.asset.utils.SpringUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
@Repository
@Service
public class CompanyService
{
    private static final Logger log = LoggerFactory.getLogger(CompanyService.class);
    @Autowired private CrudBuilder builder;

    public Company saveCompany(Company company){
        return builder.save(company);
    }
    public List<Company> companyList(){
        return builder.findAll(Company.class);
    }
    public Company findById(String companyId){
        return builder.findOne(companyId, Company.class);
    }

    public boolean delete(String companyId){
        try {
            Company company = findById(companyId);
            if(company != null) {
                builder.session().delete(company);
            }
            return true;
        }catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }
}
