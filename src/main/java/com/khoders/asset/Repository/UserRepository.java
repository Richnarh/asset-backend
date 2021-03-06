package com.khoders.asset.Repository;

import com.khoders.asset.entities.auth.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, String> {
    Optional<UserAccount> findByEmailAddress(String emailAddress);
}
