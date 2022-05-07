package com.khoders.asset.services.auth;

import com.khoders.asset.entities.auth.Role;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
@Service
public class RoleService {
    @Autowired
    private CrudBuilder builder;

    public Role save(Role role) {
        return builder.save(role);
    }

    public List<Role> roleList() {
        return builder.findAll(Role.class);
    }

    public Role findById(String roleId) {
        return builder.simpleFind(Role.class, roleId);
    }

    public boolean delete(String roleId) throws Exception {
        return builder.deleteById(roleId, Role.class);
    }
}
