package com.khoders.asset.mapper;

import com.khoders.asset.dto.DepartmentDto;
import com.khoders.asset.entities.Department;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.asset.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DepartmentMapper {
    private static final Logger log = LoggerFactory.getLogger(InventoryItemMapper.class);
    @Autowired
    private CrudBuilder builder;

    public Department toEntity(DepartmentDto dto){
        Department dep = new Department();
        if (dto.getId() != null){
            dep.setId(SpringUtils.stringToUUID(dto.getId()));
        }
        dep.setDepartmentName(dto.getDepartmentName());
        dep.setDepartmentCode(dep.getDepartmentCode());
        dep.setRefNo(dep.getRefNo());
        dep.setLastModifiedDate(LocalDateTime.now());
        return dep;
    }
    public DepartmentDto toDto(Department dep){
        DepartmentDto dto = new DepartmentDto();
        if (dep.getId() == null){
            return null;
        }
        dto.setId(SpringUtils.UUIDtoString(dep.getId()));
        dto.setDepartmentName(dep.getDepartmentName());
        dto.setDepartmentCode(dep.getDepartmentCode());
        dto.setValueDate(dep.getValueDate());
        return dto;
    }
}
