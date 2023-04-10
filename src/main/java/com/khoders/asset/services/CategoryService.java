package com.khoders.asset.services;

import com.khoders.asset.entities.Category;
import com.khoders.asset.utils.CrudBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    @Autowired
    private CrudBuilder builder;

    public Category saveCategory(Category category) {
        return builder.save(category);
    }

    public List<Category> categoryList() {
        return builder.findAll(Category.class);
    }

    public Category findById(String id) {
        return builder.simpleFind(Category.class, id);
    }

    public boolean delete(String categoryId) throws Exception {
        return builder.deleteById(categoryId, Category.class);
    }
}
