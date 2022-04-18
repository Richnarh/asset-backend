package com.khoders.asset.controller;

import com.khoders.asset.dto.LoginDto;
import com.khoders.asset.dto.UserAccountDto;
import com.khoders.asset.entities.UserAccount;
import com.khoders.asset.mapper.UserMapper;
import com.khoders.asset.services.AuthService;
import com.khoders.asset.utils.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserMapper mapper;

    @PostMapping(value = "/register")
    public ResponseEntity<UserAccount> saveUserAccount(@RequestBody UserAccountDto dto) {
        try {
            UserAccount entity = mapper.toEntity(dto);
            UserAccount user = authService.saveUser(entity);

            if (user == null) {
                return ApiResponse.error(Msg.setMsg("Unknown Error"), null);
            }
            return ApiResponse.created(Msg.setMsg("customer created successfully"), mapper.toDto(user));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }

    @GetMapping(value = "/login")
    public ResponseEntity<UserAccount> login(@RequestBody LoginDto dto) {
        UserAccount userAccount = authService.doLogin(dto);
        return ApiResponse.ok(Msg.setMsg("Login success"), mapper.toDto(userAccount));
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserAccount> findUser(@PathVariable(value = "userId") String userId) {
        try {
            UserAccount user = authService.findById(userId);
            if (user == null) {
                return ApiResponse.notFound(Msg.setMsg("No User Found"), null);
            }
            return ApiResponse.ok(Msg.setMsg("User Found"), mapper.toDto(user));
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error(e.getMessage(), null);
        }
    }
}
