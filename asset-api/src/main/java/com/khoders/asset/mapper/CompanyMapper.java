package com.khoders.asset.mapper;

import com.khoders.asset.dto.CompanyDto;
import com.khoders.asset.dto.Sql;
import com.khoders.asset.dto.authpayload.UserAccountDto;
import com.khoders.asset.exceptions.BadDataException;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.services.CompanyService;
import com.khoders.entities.Company;
import com.khoders.entities.auth.UserAccount;
import com.khoders.entities.constants.CompanyType;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class CompanyMapper {
    @Autowired
    private AppService appService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public Company toEntity(CompanyDto dto) throws DataNotFoundException {
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
        } catch (Exception ignored) {
        }

        if (dto.getPrimaryUserId() == null) {
            throw new DataNotFoundException("Specify Valid User AccountId");
        }
        UserAccount userAccount = appService.findById(UserAccount.class, dto.getPrimaryUserId());
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

    public Company createCompany(UserAccountDto userAccount) throws Exception {
        Company company = new Company();
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue(Company._companyAddress, userAccount.getEmailAddress());

        Company newCompany = jdbc.query(Sql.COMPANY_BY_EMAIL, param, new ResultSetExtractor<Company>() {

            @Override
            public Company extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    Company company = new Company();
                    company.setId(rs.getString("id"));
                    company.setCompanyAddress(rs.getString("company_address"));
                    return company;
                }
                return null;
            }
        });

        if (newCompany != null) {
            throw new BadDataException("A user with the email: " + userAccount.getEmailAddress() + " already exist");
        }
        company.setCompanyType(CompanyType.PARENT_COMPANY);
        company.setCompanyName(userAccount.getCompanyName());
        company.setEmailAddress(userAccount.getEmailAddress());
        company.setTelephone(userAccount.getPrimaryNumber());
        company.setRefNo(company.getRefNo());

        return companyService.saveCompany(company);
    }
}
