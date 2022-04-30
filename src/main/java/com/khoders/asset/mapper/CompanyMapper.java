package com.khoders.asset.mapper;

import com.khoders.asset.dto.CompanyDto;
import com.khoders.asset.entities.Company;
import com.khoders.asset.entities.auth.UserAccount;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CompanyMapper {
    @Autowired
    private CrudBuilder builder;

    public Company toEntity(CompanyDto dto) {
        Company company = new Company();
        if (dto.getId() != null) {
            company.setId(dto.getId());
        }
        company.setRefNo(company.getRefNo());
        company.setCompanyName(dto.getCompanyName());
        company.setCompanyAddress(dto.getCompanyAddress());
        company.setTelephone(dto.getTelephone());
        company.setWebsite(dto.getWebsite());
        company.setLastModifiedDate(LocalDateTime.now());

        if (dto.getUserAccountId() == null) {
            throw new DataNotFoundException("Specify Valid User AccountId");
        }
        UserAccount userAccount = builder.simpleFind(UserAccount.class,dto.getUserAccountId());
        if (userAccount != null) {
            company.setUserAccount(userAccount);
        }
        return company;
    }

    public CompanyDto toDto(Company company) {
        if (company.getId() == null) {
            return null;
        }
        CompanyDto dto = new CompanyDto();
        dto.setId(company.getId());
        dto.setCompanyName(company.getCompanyName());
        dto.setEmailAddress(company.getEmailAddress());
        dto.setTelephone(company.getTelephone());
        dto.setWebsite(company.getWebsite());
        dto.setCompanyAddress(company.getCompanyAddress());
        if (company.getUserAccount() != null) {
            dto.setUserAccountId(company.getUserAccount().getId());
            dto.setUserAccountName(company.getUserAccount().getEmailAddress());
        }
        return dto;
    }
}
