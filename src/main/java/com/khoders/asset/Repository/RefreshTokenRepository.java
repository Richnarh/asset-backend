package com.khoders.asset.Repository;

import com.khoders.asset.entities.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findById(Long id);
    Optional<RefreshToken> findByToken(String token);
}
