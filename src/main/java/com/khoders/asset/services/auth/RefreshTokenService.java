package com.khoders.asset.services.auth;

import com.khoders.asset.Repository.RefreshTokenRepository;
import com.khoders.asset.Repository.UserRepository;
import com.khoders.asset.entities.auth.RefreshToken;
import com.khoders.asset.entities.auth.UserAccount;
import com.khoders.asset.exceptions.TokenRefreshException;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.utilities.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
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
        refreshToken.setIssuedAt(Date.from(Instant.now().truncatedTo(ChronoUnit.SECONDS)));
        refreshToken.setExpiryDate(Date.from(Instant.now().truncatedTo(ChronoUnit.SECONDS).plus(3,ChronoUnit.MINUTES)));
        refreshToken.setToken(builder.genId());

        refreshToken = builder.save(refreshToken);
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
