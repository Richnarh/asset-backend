package com.khoders.asset.mapper;

import com.khoders.asset.dto.authpayload.RoleDto;
import com.khoders.asset.entities.auth.Role;
import com.khoders.asset.entities.constants.UserRole;
import com.khoders.resource.spring.CrudBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    private static final Logger log = LoggerFactory.getLogger(RoleMapper.class);
    @Autowired
    private CrudBuilder builder;

    public Role toEntity(RoleDto dto) {
        Role role = new Role();
        if (dto.getId() != null) {
            role.setId(dto.getId());
        }
        role.setRoleName(UserRole.valueOf(dto.getRoleName()));
        role.setRefNo(role.getRefNo());
        return role;
    }

    public RoleDto toDto(Role role) {
        RoleDto dto = new RoleDto();
        if (role.getId() == null) {
            return null;
        }
        dto.setId(role.getId());
        try {
            dto.setRoleName(role.getRoleName().name());
        }catch (Exception ignored){}
        return dto;
    }
}
