package com.khoders.asset.mapper;

import com.khoders.asset.dto.CompanyDto;
import com.khoders.asset.dto.authpayload.UserAccountDto;
import com.khoders.asset.entities.Company;
import com.khoders.asset.entities.auth.UserAccount;
import com.khoders.asset.entities.constants.CompanyType;
import com.khoders.asset.services.CompanyService;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    @Autowired
    private CrudBuilder builder;
    @Autowired private CompanyService companyService;

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
        try {
            company.setCompanyType(CompanyType.valueOf(dto.getCompanyType()));
        }catch (Exception ignored){}

        if (dto.getPrimaryUserId() == null) {
            throw new DataNotFoundException("Specify Valid User AccountId");
        }
        UserAccount userAccount = builder.simpleFind(UserAccount.class, dto.getPrimaryUserId());
        if (userAccount != null) {
            company.setPrimaryUser(userAccount);
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
        dto.setCompanyType(company.getCompanyType().getLabel());
        if (company.getPrimaryUser() != null) {
            dto.setPrimaryUserId(company.getPrimaryUser().getId());
            dto.setPrimaryUser(company.getPrimaryUser().getEmailAddress());
        }
        return dto;
    }

    public Company createCompany(UserAccountDto userAccount){
        Company company = new Company();
        company.setCompanyType(CompanyType.PARENT_COMPANY);
        company.setCompanyName(userAccount.getCompanyName());
        company.setEmailAddress(userAccount.getEmailAddress());
        company.setTelephone(userAccount.getPrimaryNumber());
        company.setRefNo(company.getRefNo());

        return companyService.saveCompany(company);
    }
}
