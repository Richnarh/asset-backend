package com.khoders.asset.mapper;

import com.khoders.asset.dto.DepartmentDto;
import com.khoders.entities.Department;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentDto dto) {
        Department dep = new Department();
        if (dto.getId() != null) {
            dep.setId(dto.getId());
        }
        dep.setDepartmentName(dto.getDepartmentName());
        dep.setDepartmentCode(dto.getDepartmentCode());
        dep.setRefNo(dep.getRefNo());
        return dep;
    }

    public DepartmentDto toDto(Department dep) {
        DepartmentDto dto = new DepartmentDto();
        if (dep.getId() == null) {
            return null;
        }
        dto.setId(dep.getId());
        dto.setDepartmentName(dep.getDepartmentName());
        dto.setDepartmentCode(dep.getDepartmentCode());
        dto.setValueDate(DateUtil.parseLocalDateString(dep.getValueDate(), Pattern.ddMMyyyy));
        return dto;
    }
}
