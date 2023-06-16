package com.khoders.asset.controller.auth;

import com.khoders.asset.dto.authpayload.*;
import com.khoders.asset.event.SignupCompleteEvent;
import com.khoders.asset.exceptions.BadDataException;
import com.khoders.asset.exceptions.InternalErrException;
import com.khoders.asset.exceptions.TokenRefreshException;
import com.khoders.asset.jwt.JwtService;
import com.khoders.asset.mapper.AuthMapper;
import com.khoders.asset.services.auth.AuthService;
import com.khoders.asset.services.auth.RefreshTokenService;
import com.khoders.asset.utils.ApiEndpoint;
import com.khoders.entities.auth.RefreshToken;
import com.khoders.entities.auth.UserAccount;
import com.khoders.entities.auth.VerificationToken;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import com.khoders.springapi.AppService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Tag(name = "Authentication - Endpoint")
@RequestMapping(ApiEndpoint.AUTH_ENDPOINT)
public class AuthController {
    @Autowired private AuthService authService;
    @Autowired private AuthMapper mapper;
    @Autowired  private RefreshTokenService refreshTokenService;
    @Autowired private JwtService jwtService;
    @Autowired private ApplicationEventPublisher publisher;
    @Autowired private AppService appService;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signup(@RequestBody UserAccountDto dto, final HttpServletRequest request) throws Exception {
        try {
            JwtResponse user = authService.save(dto);
            if (user == null) {
                throw new BadDataException(Msg.UNKNOWN_ERROR);
            }
            UserAccount userAccount = appService.findById(UserAccount.class, user.getId());
            publisher.publishEvent(new SignupCompleteEvent(userAccount,applicationUrl(request)));
            return ApiResponse.created("Registration successful", user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
        }
    }
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken = authService.findByToken(token);
        if(theToken.getUserAccount().isEnabled()){
            return "This account already verified, please login.";
        }
        String verifyResult = authService.validateToken(token);
        if(verifyResult.equalsIgnoreCase("valid")){
            return "Email verified successfully. Now you can login";
        }
        return "Invalid verification token";
    }
    private String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName() +":"+request.getServerPort()+request.getContextPath();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        try {
            return ApiResponse.ok("Login success", mapper.toJwtResponse(loginRequest));
        } catch (Exception e) {
            throw new InternalErrException(e.getMessage());
        }
    }

    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest refreshRequest) {
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
    public ResponseEntity<UserAccount> findUser(@PathVariable(value = "userId") String userId) throws Exception {
        try {
            UserAccount user = authService.findById(userId);
            if (user == null) {
                return ApiResponse.notFound("No User Found", null);
            }
            return ApiResponse.ok("User Found", mapper.toDto(user));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrException(e.getMessage());
        }
    }
}
