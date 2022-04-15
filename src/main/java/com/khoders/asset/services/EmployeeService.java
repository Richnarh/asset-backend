package com.khoders.asset.services;

import com.khoders.asset.entities.Department;
import com.khoders.asset.entities.Employee;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
@Service
public class EmployeeService {
    @Autowired private CrudBuilder builder;

    public Employee save(Employee department){
        return builder.save(department);
    }
    public List<Employee> employees(){
        return builder.findAll(Employee.class);
    }
    public Employee findById(String employeeId){
        return builder.findOne(employeeId,Employee.class);
    }
    public boolean delete(String employeeId){
        return builder.deleteById(employeeId,Employee.class);
    }
}
