package com.khoders.asset.mapper;

import com.khoders.asset.dto.EmployeeDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.entities.Department;
import com.khoders.entities.Employee;
import com.khoders.resource.enums.Title;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmployeeMapper {
    @Autowired
    private AppService appService;

    public Employee toEntity(EmployeeDto dto) throws Exception {
        Employee employee = new Employee();
        if (dto.getId() != null) {
            employee.setId(dto.getId());
        }
        employee.setRefNo(employee.getRefNo());
        employee.setFirstName(dto.getFirstName());

        employee.setSurname(dto.getSurname());
        employee.setOtherName(dto.getOtherName());
        employee.setEmailAddress(dto.getEmailAddress());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setLastModifiedDate(LocalDateTime.now());
        try {
            employee.setTitle(Title.valueOf(dto.getTitle()));
        } catch (Exception ignored) {
        }
        if (dto.getDepartmentId() == null) {
            throw new DataNotFoundException("Specify Valid DepartmentId");
        }

        Department department = appService.findById(Department.class, dto.getDepartmentId());
        if (department != null) {
            employee.setDepartment(department);
        }
        return employee;
    }

    public EmployeeDto toDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        if (employee.getId() == null) return null;
        dto.setId(employee.getId());
        try {
            dto.setTitle(employee.getTitle().getLabel());
        } catch (Exception ignored) {
        }
        dto.setFirstName(employee.getFirstName());
        dto.setSurname(employee.getSurname());
        dto.setOtherName(employee.getOtherName());
        dto.setEmailAddress(employee.getEmailAddress());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setValueDate(DateUtil.parseLocalDateString(employee.getValueDate(), Pattern.ddMMyyyy));
        if (employee.getDepartment() != null) {
            dto.setDepartmentName(employee.getDepartment().getDepartmentName());
            dto.setDepartmentId(employee.getDepartment().getId());
        }
        return dto;
    }
}
