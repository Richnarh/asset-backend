package com.khoders.asset.controller;

import com.khoders.asset.dto.CategoryDto;
import com.khoders.asset.dto.LocationDto;
import com.khoders.asset.entities.Category;
import com.khoders.asset.entities.Location;
import com.khoders.asset.mapper.CategoryMapper;
import com.khoders.asset.services.CategoryService;
import com.khoders.asset.utils.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {
    private static final Logger log = getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper mapper;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto dto) {
        try {
            Category entity = mapper.toEntity(dto);
            Category category = categoryService.saveCategory(entity);
            if (category == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.created(Msg.CREATED, mapper.toDto(category));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryDto dto) {
        try {
            Category category = categoryService.findById(dto.getId());
            if (category == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, null);
            }
            Category c = categoryService.saveCategory(category);
            if (c == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.ok(Msg.UPDATED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Category>> getAssetTransfers() {
        List<Category> categories = categoryService.categoryList();
        List<CategoryDto> dtoList = new LinkedList<>();
        categories.forEach(category -> {
            dtoList.add(mapper.toDto(category));
        });
        if (dtoList.isEmpty()) {
            return ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findSingle(@PathVariable(value = "categoryId") String categoryId) {
        try {
            Category category = categoryService.findById(categoryId);
            if (category == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, new CategoryDto());
            }
            return ApiResponse.ok(Msg.RECORD_FOUND, mapper.toDto(category));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value = "categoryId") String categoryId) {
        try {
            if (categoryService.delete(categoryId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error(Msg.setMsg("Could Not Delete Category"), false);
    }
}
