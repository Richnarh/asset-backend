package com.khoders.asset.controller.auth;

import com.khoders.asset.dto.authpayload.*;
import com.khoders.asset.entities.auth.RefreshToken;
import com.khoders.asset.entities.auth.UserAccount;
import com.khoders.asset.exceptions.TokenRefreshException;
import com.khoders.asset.jwt.JwtService;
import com.khoders.asset.mapper.AuthMapper;
import com.khoders.asset.services.auth.AuthService;
import com.khoders.asset.services.auth.RefreshTokenService;
import com.khoders.asset.utils.ApiResponse;
import com.khoders.resource.utilities.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600L)
@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthMapper mapper;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserAccount> register(@RequestBody UserAccountDto dto) {
        try {
            System.out.println("\n registering  ---- "+ SystemUtils.KJson().toJson(dto));
            UserAccount entity = mapper.toEntity(dto);
            UserAccount user = authService.saveUser(entity);

            if (user == null) {
                return ApiResponse.error("Unknown Error", null);
            }
            return ApiResponse.created("Registration successful", mapper.toDto(user));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            return ApiResponse.ok("Login success", mapper.toJwtResponse(loginRequest));
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage(), null);
        }
    }
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest refreshRequest){
        String requestRefreshToken = refreshRequest.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserAccount)
                .map(user -> {
                    String token = jwtService.generateTokenFromEmailAddress(user.getEmailAddress());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserAccount> findUser(@PathVariable(value = "userId") String userId) {
        try {
            UserAccount user = authService.findById(userId);
            if (user == null) {
                return ApiResponse.notFound("No User Found", null);
            }
            return ApiResponse.ok("User Found", mapper.toDto(user));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }
}
