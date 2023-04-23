package com.khoders.asset.services;

import com.khoders.entities.Category;
import com.khoders.springapi.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    @Autowired
    private AppService appService;

    public Category saveCategory(Category category) {
        return appService.save(category);
    }

    public List<Category> categoryList() {
        return appService.findAll(Category.class);
    }

    public Category findById(String id) {
        return appService.findById(Category.class, id);
    }

    public boolean delete(String categoryId) throws Exception {
        return appService.deleteById(Category.class, categoryId);
    }
}
