package com.khoders.asset.services.auth;

import com.khoders.asset.Repository.RefreshTokenRepository;
import com.khoders.asset.Repository.UserRepository;
import com.khoders.asset.entities.auth.RefreshToken;
import com.khoders.asset.entities.auth.UserAccount;
import com.khoders.asset.exceptions.TokenRefreshException;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class RefreshTokenService {
    @Value("${khoders.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;
    @Autowired
    private RefreshTokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CrudBuilder builder;

    public Optional<RefreshToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
    public RefreshToken createRefreshToken(String userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserAccount(builder.simpleFind(UserAccount.class, userId));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(builder.genId());
        refreshToken = builder.save(refreshToken);
        return refreshToken;
    }
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            tokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new sign in request");
        }
        return token;
    }
}
