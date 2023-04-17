package com.khoders.asset.controller.auth;

import com.khoders.asset.dto.authpayload.*;
import com.khoders.asset.entities.auth.RefreshToken;
import com.khoders.asset.entities.auth.UserAccount;
import com.khoders.asset.exceptions.BadDataException;
import com.khoders.asset.exceptions.TokenRefreshException;
import com.khoders.asset.jwt.JwtService;
import com.khoders.asset.mapper.AuthMapper;
import com.khoders.asset.services.auth.AuthService;
import com.khoders.asset.services.auth.RefreshTokenService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "Authentication - Endpoint")
@RequestMapping(ApiEndpoint.AUTH_ENDPOINT)
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthMapper mapper;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signup(@RequestBody UserAccountDto dto) throws Exception{
        try {
            JwtResponse user = authService.save(dto);
            if (user == null) {
                throw new BadDataException(Msg.UNKNOWN_ERROR);
            }
            return ApiResponse.created("Registration successful", user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InterruptedException(e.getMessage());
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest)throws Exception {
        try {
            return ApiResponse.ok("Login success", mapper.toJwtResponse(loginRequest));
        } catch (Exception e) {
            throw new InterruptedException(e.getMessage());
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
    public ResponseEntity<UserAccount> findUser(@PathVariable(value = "userId") String userId) throws Exception{
        try {
            UserAccount user = authService.findById(userId);
            if (user == null) {
                return ApiResponse.notFound("No User Found", null);
            }
            return ApiResponse.ok("User Found", mapper.toDto(user));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InterruptedException(e.getMessage());
        }
    }
}
