package com.khoders.asset.services;

import com.khoders.asset.entities.Department;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private CrudBuilder builder;

    public Department save(Department department) {
        return builder.save(department);
    }

    public List<Department> departments() {
        return builder.findAll(Department.class);
    }

    public Department findById(String departmentId) {
        return builder.findOne(departmentId, Department.class);
    }

    public boolean delete(String departmentId) throws Exception {
        return builder.deleteById(departmentId, Department.class);
    }
}
