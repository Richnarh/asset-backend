package com.khoders.asset.services;

import com.khoders.asset.entities.Department;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
@Service
public class DepartmentService {
    @Autowired private CrudBuilder builder;

    public Department save(Department department){
        return builder.save(department);
    }
    public List<Department> departments(){
        return builder.findAll(Department.class);
    }
    public Department findById(String departmentId){
        return builder.findOne(departmentId,Department.class);
    }
    public boolean delete(String departmentId){
        return builder.deleteById(departmentId,Department.class);
    }
}
