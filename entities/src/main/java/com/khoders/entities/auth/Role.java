package com.khoders.entities.auth;

import com.khoders.entities.Ref;
import com.khoders.entities.constants.UserRole;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends Ref {
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 20)
    private UserRole roleName;

    public UserRole getRoleName() {
        return roleName;
    }

    public void setRoleName(UserRole roleName) {
        this.roleName = roleName;
    }
}
