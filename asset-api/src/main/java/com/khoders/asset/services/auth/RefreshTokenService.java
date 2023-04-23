package com.khoders.asset.services.auth;

import com.khoders.asset.exceptions.TokenRefreshException;
import com.khoders.asset.repository.RefreshTokenRepository;
import com.khoders.asset.repository.UserRepository;
import com.khoders.entities.auth.RefreshToken;
import com.khoders.entities.auth.UserAccount;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class RefreshTokenService {
    @Value("${asset.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;
    @Autowired
    private RefreshTokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppService appService;

    public Optional<RefreshToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(String userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserAccount(appService.findById(UserAccount.class, userId));
        refreshToken.setIssuedAt(Date.from(Instant.now().truncatedTo(ChronoUnit.SECONDS)));
        refreshToken.setExpiryDate(Date.from(Instant.now().truncatedTo(ChronoUnit.SECONDS).plus(3, ChronoUnit.MINUTES)));
        refreshToken.setToken(appService.genId());

        refreshToken = appService.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Date.from(Instant.now())) < 0) {
            tokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new sign in request");
        }
        return token;
    }
}
