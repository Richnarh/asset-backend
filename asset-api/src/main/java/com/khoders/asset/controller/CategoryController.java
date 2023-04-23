package com.khoders.asset.controller;

import com.khoders.asset.dto.CategoryDto;
import com.khoders.asset.exceptions.BadDataException;
import com.khoders.asset.exceptions.InternalErrException;
import com.khoders.asset.mapper.CategoryMapper;
import com.khoders.asset.services.CategoryService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.entities.Category;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@Tag(name = "Category - Endpoint")
@RequestMapping(ApiEndpoint.CATEGORY_ENDPOINT)
public class CategoryController {
    private static final Logger log = getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper mapper;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto dto) throws Exception {
        try {
            Category entity = mapper.toEntity(dto);
            Category category = categoryService.saveCategory(entity);
            if (category == null) {
                throw new BadDataException(Msg.UNKNOWN_ERROR);
            }
            return ApiResponse.created(Msg.CREATED, mapper.toDto(category));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryDto dto) throws Exception {
        try {
            Category category = categoryService.findById(dto.getId());
            if (category == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, null);
            }
            Category c = categoryService.saveCategory(category);
            if (c == null) {
                throw new BadDataException(Msg.UNKNOWN_ERROR);
            }
            return ApiResponse.ok(Msg.UPDATED, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
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
    public ResponseEntity<Category> findSingle(@PathVariable(value = "categoryId") String categoryId) throws Exception {
        try {
            Category category = categoryService.findById(categoryId);
            if (category == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, new CategoryDto());
            }
            return ApiResponse.ok(Msg.RECORD_FOUND, mapper.toDto(category));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
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
        return ApiResponse.error("Could Not Delete Category", false);
    }
}
