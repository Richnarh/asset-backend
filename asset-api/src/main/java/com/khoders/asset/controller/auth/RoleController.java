package com.khoders.asset.controller.auth;

import com.khoders.asset.dto.LocationDto;
import com.khoders.asset.dto.authpayload.RoleDto;
import com.khoders.asset.exceptions.BadDataException;
import com.khoders.asset.mapper.RoleMapper;
import com.khoders.asset.services.auth.RoleService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.entities.auth.Role;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@RestController
@Tag(name = "Role - Endpoint")
@RequestMapping(ApiEndpoint.ROLE_ENDPOINT)
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMapper mapper;

    @PostMapping
    public ResponseEntity<Role> create(@Valid @RequestBody RoleDto dto) throws Exception {
        try {
            Role entity = mapper.toEntity(dto);
            Role role = roleService.save(entity);
            if (role == null) {
                throw new BadDataException(Msg.UNKNOWN_ERROR);
            }
            return ApiResponse.created(Msg.CREATED, mapper.toDto(role));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InterruptedException(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Role>> list() {
        List<Role> roles = roleService.roleList();
        List<RoleDto> dtoList = new LinkedList<>();
        roles.forEach(role -> {
            dtoList.add(mapper.toDto(role));
        });
        if (dtoList.isEmpty()) {
            return ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        return ApiResponse.ok(Msg.RECORD_FOUND, dtoList);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Role> findSingle(@PathVariable(value = "roleId") String roleId) {
        try {
            Role role = roleService.findById(roleId);
            if (role == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, new LocationDto());
            }
            return ApiResponse.ok(Msg.RECORD_FOUND, mapper.toDto(role));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
    }

    @PutMapping
    public ResponseEntity<Role> update(@RequestBody RoleDto dto) throws Exception {
        try {
            Role role = roleService.findById(dto.getId());
            if (role == null) {
                return ApiResponse.notFound(Msg.RECORD_NOT_FOUND, null);
            }
            Role entity = mapper.toEntity(dto);
            Role userRole = roleService.save(entity);
            if (userRole == null) {
                throw new BadDataException(Msg.UNKNOWN_ERROR);
            }
            return ApiResponse.ok(Msg.UPDATED, mapper.toDto(userRole));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InterruptedException(e.getMessage());
        }
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "roleId") String roleId) {
        try {
            if (roleService.delete(roleId)) return ApiResponse.ok(Msg.DELETED, true);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), false);
        }
        return ApiResponse.error("Could Not Delete Roles", false);
    }
}
