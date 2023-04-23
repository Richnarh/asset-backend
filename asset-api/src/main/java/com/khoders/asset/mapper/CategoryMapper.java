package com.khoders.asset.mapper;

import com.khoders.asset.dto.CategoryDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.entities.Category;
import com.khoders.entities.Company;
import com.khoders.entities.auth.UserAccount;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import com.khoders.springapi.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    private static final Logger log = LoggerFactory.getLogger(CategoryMapper.class);
    @Autowired private AppService appService;

    public Category toEntity(CategoryDto dto) throws Exception {
        Category category = new Category();
        if (dto.getId() != null) {
            category.setId(dto.getId());
        }
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        category.setRefNo(category.getRefNo());
        if(dto.getCompanyId() == null){
            log.error("CompanyId not found");
            throw new DataNotFoundException("CompanyId cannot be null");
        }
        if(dto.getUserAccountId() == null){
            log.error("UserAccountId not found");
            throw new DataNotFoundException("UserAccountId cannot be null");
        }
        category.setCompany(appService.findById(Company.class, dto.getCompanyId()));
        category.setUserAccount(appService.findById(UserAccount.class, dto.getUserAccountId()));
        return category;
    }

    public CategoryDto toDto(Category category) {
        CategoryDto dto = new CategoryDto();
        if (category == null) {
            return null;
        }
        dto.setId(category.getId());
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());
        dto.setValueDate(DateUtil.parseLocalDateString(category.getValueDate(), Pattern.ddMMyyyy));
        return dto;
    }
}
