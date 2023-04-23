package com.khoders.asset.services.auth;

import com.khoders.entities.auth.Role;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private AppService appService;

    public Role save(Role role) {
        return appService.save(role);
    }

    public List<Role> roleList() {
        return appService.findAll(Role.class);
    }

    public Role findById(String roleId) {
        return appService.findById(Role.class, roleId);
    }

    public boolean delete(String roleId) throws Exception {
        return appService.deleteById(Role.class, roleId);
    }
}
