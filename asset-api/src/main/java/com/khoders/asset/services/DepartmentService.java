package com.khoders.asset.services;

import com.khoders.entities.Department;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private AppService appService;

    public Department save(Department department) {
        return appService.save(department);
    }

    public List<Department> departments() {
        return appService.findAll(Department.class);
    }

    public Department findById(String departmentId) {
        return appService.findById(Department.class, departmentId);
    }

    public boolean delete(String departmentId) throws Exception {
        return appService.deleteById(Department.class, departmentId);
    }
}
