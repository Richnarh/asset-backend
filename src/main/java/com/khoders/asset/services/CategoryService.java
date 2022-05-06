package com.khoders.asset.services;

import com.khoders.asset.entities.Category;
import com.khoders.resource.spring.CrudBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
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

    public boolean delete(String categoryId) {
        return builder.deleteById(categoryId, Category.class);
    }
}
