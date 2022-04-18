package com.khoders.asset.mapper;

import com.khoders.asset.dto.DepartmentDto;
import com.khoders.asset.entities.Department;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DepartmentMapper {
    private static final Logger log = LoggerFactory.getLogger(InventoryItemMapper.class);
    @Autowired
    private CrudBuilder builder;

    public Department toEntity(DepartmentDto dto) {
        Department dep = new Department();
        if (dto.getId() != null) {
            dep.setId(dto.getId());
        } else {
            dep.setId(builder.genId());
        }
        dep.setDepartmentName(dto.getDepartmentName());
        dep.setDepartmentCode(dto.getDepartmentCode());
        dep.setRefNo(dep.getRefNo());
        dep.setLastModifiedDate(LocalDateTime.now());
        if (dto.getValueDate() == null) {
            dep.setValueDate(LocalDate.now());
        } else {
            dep.setValueDate(DateUtil.parseLocalDate(dto.getValueDate(), Pattern._yyyyMMdd));
        }
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
