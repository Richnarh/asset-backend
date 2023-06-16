package com.khoders.asset.repository;

import com.khoders.entities.auth.Role;
import com.khoders.entities.constants.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRoleName(UserRole userRole);
}
