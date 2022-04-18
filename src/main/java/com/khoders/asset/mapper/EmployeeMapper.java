package com.khoders.asset.mapper;

import com.khoders.asset.dto.EmployeeDto;
import com.khoders.asset.entities.Department;
import com.khoders.asset.entities.Employee;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.enums.Title;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class EmployeeMapper {
    private static final Logger log = LoggerFactory.getLogger(EmployeeMapper.class);
    @Autowired
    private CrudBuilder builder;

    public Employee toEntity(EmployeeDto dto) {
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
        }catch (Exception ignored){}
        if (dto.getDepartmentId() == null) {
            throw new DataNotFoundException("Specify Valid DepartmentId");
        }
        if (dto.getValueDate() == null) {
            employee.setValueDate(LocalDate.now());
        } else {
            employee.setValueDate(DateUtil.parseLocalDate(dto.getValueDate(), Pattern._yyyyMMdd));
        }
        Department department = builder.findOne(dto.getDepartmentId(), Department.class);
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
        }catch (Exception ignored){}
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
