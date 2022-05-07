package com.khoders.asset.mapper;

import com.khoders.asset.dto.CategoryDto;
import com.khoders.asset.entities.Category;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    private static final Logger log = LoggerFactory.getLogger(CategoryMapper.class);

    public Category toEntity(CategoryDto dto) {
        Category category = new Category();
        if (dto.getId() != null) {
            category.setId(dto.getId());
        }
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        category.setRefNo(category.getRefNo());

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
