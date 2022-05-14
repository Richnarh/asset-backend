package com.khoders.asset.mapper;

import com.khoders.asset.Repository.RoleRepository;
import com.khoders.asset.Repository.UserRepository;
import com.khoders.asset.dto.authpayload.JwtResponse;
import com.khoders.asset.dto.authpayload.LoginRequest;
import com.khoders.asset.dto.authpayload.RoleDto;
import com.khoders.asset.dto.authpayload.UserAccountDto;
import com.khoders.asset.entities.auth.RefreshToken;
import com.khoders.asset.entities.auth.Role;
import com.khoders.asset.entities.auth.UserAccount;
import com.khoders.asset.entities.constants.UserRole;
import com.khoders.asset.jwt.JwtService;
import com.khoders.asset.services.auth.AuthService;
import com.khoders.asset.services.auth.RefreshTokenService;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AuthMapper {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthService authService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenService refreshTokenService;

    public UserAccount toEntity(UserAccountDto dto) {
        UserAccount user = new UserAccount();
        if (dto.getId() != null) {
            user.setId(dto.getId());
        }
        user.setRefNo(user.getRefNo());
        user.setEmailAddress(dto.getEmailAddress());
        user.setPrimaryNumber(dto.getPrimaryNumber());
        user.setSecondNumber(dto.getSecondNumber());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));

        Set<String> strRoles = dto.getUserRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null){
            Role userRole = roleRepository.findByRoleName(UserRole.ROLE_USER)
                    .orElseThrow(() -> new DataNotFoundException("Error: Role is not found."));
            roles.add(userRole);
        }
        else{
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(UserRole.ROLE_ADMIN)
                                .orElseThrow(() -> new DataNotFoundException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByRoleName(UserRole.ROLE_MODERATOR)
                                .orElseThrow(() -> new DataNotFoundException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(UserRole.ROLE_USER)
                                .orElseThrow(() -> new DataNotFoundException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        return user;
    }

    public UserAccountDto toDto(UserAccount user){
        UserAccountDto dto = new UserAccountDto();
        if (user == null) {
            return null;
        }
        dto.setId(user.getId());
        dto.setEmailAddress(user.getEmailAddress());
        dto.setPrimaryNumber(user.getPrimaryNumber());
        dto.setSecondNumber(user.getSecondNumber());
        dto.setValueDate(DateUtil.parseLocalDateString(user.getValueDate(), Pattern.ddMMyyyy));
        return dto;
    }

    public JwtResponse toJwtResponse(LoginRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();

        if (loginRequest.getEmailAddress() == null){
            throw new DataNotFoundException("Please enter email");
        }
        if (loginRequest.getPassword() == null){
            throw new DataNotFoundException("Please enter password");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(), loginRequest.getPassword())
        );
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserAccount userAccount = userRepository.findByEmailAddress(userDetails.getUsername()).orElseThrow(() -> new DataNotFoundException("User Not Found"));
        Set<RoleDto> roles = new HashSet<>();
        userAccount.getRoles().forEach(item ->{
            RoleDto dto = new RoleDto();
            dto.setId(item.getId());
            dto.setRoleName(item.getRoleName().name());
            roles.add(dto);
        });

        String jwtToken = jwtService.generateToken(userDetails.getUsername());

        jwtResponse.setAccessToken(jwtToken);
        jwtResponse.setId(userAccount.getId());
        jwtResponse.setValueDate(DateUtil.parseLocalDateString(userAccount.getValueDate(), Pattern.ddMMyyyy));
        jwtResponse.setRoleList(roles);

        // Todo: to reviewed and error fix
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userAccount.getId());
        jwtResponse.setExpiryDate(String.valueOf(refreshToken.getExpiryDate()));
        jwtResponse.setRefreshToken(refreshToken.getToken());

        return jwtResponse;
    }
}
