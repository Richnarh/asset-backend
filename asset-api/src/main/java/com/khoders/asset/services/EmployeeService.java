package com.khoders.asset.services;

import com.khoders.entities.Employee;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private AppService appService;
    public Employee save(Employee department) {
        return appService.save(department);
    }

    public List<Employee> employees() {
        return appService.findAll(Employee.class);
    }

    public Employee findById(String employeeId) {
        return appService.findById(Employee.class, employeeId);
    }

    public boolean delete(String employeeId) throws Exception {
        return appService.deleteById(Employee.class, employeeId);
    }
}
