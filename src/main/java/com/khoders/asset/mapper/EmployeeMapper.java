package com.khoders.asset.mapper;

import com.khoders.asset.dto.EmployeeDto;
import com.khoders.asset.entities.Company;
import com.khoders.asset.entities.Department;
import com.khoders.asset.entities.Employee;
import com.khoders.asset.entities.UserAccount;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.asset.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmployeeMapper {
    private static final Logger log = LoggerFactory.getLogger(EmployeeMapper.class);
    @Autowired
    private CrudBuilder builder;

    public Employee toEntity(EmployeeDto dto) throws Exception {
        Employee employee = new Employee();
        if (dto.getId() != null) {
            employee.setId(SpringUtils.stringToUUID(dto.getId()));
        }
        employee.setRefNo(employee.getRefNo());
        employee.setFirstName(dto.getFirstName());
        employee.setTitle(dto.getTitle());
        employee.setSurname(dto.getSurname());
        employee.setOtherName(dto.getOtherName());
        employee.setEmailAddress(dto.getEmailAddress());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setLastModifiedDate(LocalDateTime.now());
        if (dto.getDepartmentId() == null) {
            throw new Exception("Specify Valid DepartmentId");
        }
        if (dto.getCompanyId() == null) {
            throw new Exception("Specify Valid Company");
        }
        if (dto.getUserAccountId() == null) {
            throw new Exception("Specify Valid UserAccountId");
        }
        Department department = builder.findOne(dto.getDepartmentId(), Department.class);
        if (department != null) {
            employee.setDepartment(department);
        }
        Company company = builder.findOne(dto.getCompanyId(), Company.class);
        if (company != null) {
            employee.setCompany(company);
        }
        UserAccount userAccount = builder.findOne(dto.getUserAccountId(), UserAccount.class);
        if (userAccount != null) {
            employee.setLastModifiedBy(userAccount.getEmailAddress());
        }
        return employee;
    }

    public EmployeeDto toDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        if (employee.getId() == null) {
            return null;
        }
        dto.setRefNo(employee.getRefNo());
        dto.setTitle(employee.getTitle());
        dto.setFirstName(dto.getFirstName());
        dto.setSurname(dto.getSurname());
        dto.setOtherName(dto.getOtherName());
        dto.setEmailAddress(dto.getEmailAddress());
        dto.setPhoneNumber(dto.getPhoneNumber());
        dto.setValueDate(employee.getValueDate());
        if (employee.getDepartment() != null) {
            dto.setDepartmentName(employee.getDepartment().getDepartmentName());
            dto.setDepartmentId(SpringUtils.UUIDtoString(employee.getDepartment().getId()));
        }
        return dto;
    }
}
