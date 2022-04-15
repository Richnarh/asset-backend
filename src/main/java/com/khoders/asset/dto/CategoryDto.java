package com.khoders.asset.dto;

import javax.persistence.Column;

public class CategoryDto extends BaseDto
{
    private String categoryName;
    private String description;

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
