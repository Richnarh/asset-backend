package com.khoders.asset.mapper;

import com.khoders.asset.dto.CategoryDto;
import com.khoders.asset.entities.Category;
import com.khoders.asset.services.CategoryService;
import com.khoders.asset.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CategoryMapper
{
    private static final Logger log = LoggerFactory.getLogger(CategoryMapper.class);
    public Category toEntity(CategoryDto dto)
    {
        Category category = new Category();
        if(dto.getId() != null){
            category.setId(SpringUtils.stringToUUID(dto.getId()));
        }
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        category.setRefNo(category.getRefNo());
        category.setLastModifiedDate(LocalDateTime.now());

        return category;
    }

    public CategoryDto toDto(Category category)
    {
        CategoryDto dto = new CategoryDto();
        if(category == null){
            return null;
        }
        dto.setId(category.getId().toString());
        dto.setRefNo(category.getRefNo());
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());
        dto.setValueDate(category.getValueDate());
        return dto;
    }
}
