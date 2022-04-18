package com.khoders.asset.controller;

import com.khoders.asset.dto.DepartmentDto;
import com.khoders.asset.entities.Department;
import com.khoders.asset.mapper.DepartmentMapper;
import com.khoders.asset.services.DepartmentService;
import com.khoders.asset.utils.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/v1/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DepartmentMapper mapper;

    @PostMapping
    public ResponseEntity<Department> create(@RequestBody DepartmentDto dto) {
        try {
            Department entity = mapper.toEntity(dto);
            Department department = departmentService.save(entity);
            if (department == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.created(Msg.CREATED, mapper.toDto(department));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Department>> list() {
        List<Department> departmentList = departmentService.departments();
        List<DepartmentDto> dtoList = new LinkedList<>();
        departmentList.forEach(dep -> {
            dtoList.add(mapper.toDto(dep));
        });
        if (dtoList.isEmpty()) {
            return ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<Department> findSingle(@PathVariable(value = "departmentId") String departmentId) {
        try {
            Department department = departmentService.findById(departmentId);
            if (department == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, new DepartmentDto());
            }
            return ApiResponse.ok(Msg.RECORD_FOUND, mapper.toDto(department));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
    }

    @PutMapping
    public ResponseEntity<Department> update(@RequestBody DepartmentDto dto) {
        try {
            Department department = departmentService.findById(dto.getId());
            if (department == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, null);
            }
            Department dept = departmentService.save(department);
            if (dept == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.ok(Msg.UPDATED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Object> deleteAssetTransfer(@PathVariable(value = "departmentId") String departmentId) {
        try {
            if (departmentService.delete(departmentId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete Department", false);
    }
}
