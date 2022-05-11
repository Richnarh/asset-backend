package com.khoders.asset.controller;

import com.khoders.asset.dto.EmployeeDto;
import com.khoders.asset.entities.Employee;
import com.khoders.asset.mapper.EmployeeMapper;
import com.khoders.asset.services.EmployeeService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.spring.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(ApiEndpoint.EMPLOYEE_ENDPOINT)
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeMapper mapper;

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody EmployeeDto dto) {
        try {
            Employee entity = mapper.toEntity(dto);
            Employee Employee = employeeService.save(entity);
            if (Employee == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.created(Msg.CREATED, mapper.toDto(Employee));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Employee>> list() {
        List<Employee> employeeList = employeeService.employees();
        List<EmployeeDto> dtoList = new LinkedList<>();
        employeeList.forEach(employee -> {
            dtoList.add(mapper.toDto(employee));
        });
        if (dtoList.isEmpty()) {
            return ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> findSingle(@PathVariable(value = "employeeId") String employeeId) {
        try {
            Employee employee = employeeService.findById(employeeId);
            if (employee == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, new EmployeeDto());
            }
            return ApiResponse.ok(Msg.RECORD_FOUND, mapper.toDto(employee));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
    }

    @PutMapping
    public ResponseEntity<Employee> update(@RequestBody EmployeeDto dto) {
        try {
            Employee employee = employeeService.findById(dto.getId());
            if (employee == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, null);
            }
            Employee entity = mapper.toEntity(dto);
            Employee emp = employeeService.save(entity);
            if (emp == null) {
                return ApiResponse.error(Msg.UNKNOWN_ERROR, null);
            }
            return ApiResponse.ok(Msg.UPDATED, mapper.toDto(emp));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "employeeId") String employeeId) {
        try {
            if (employeeService.delete(employeeId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete Employee", false);
    }
}
