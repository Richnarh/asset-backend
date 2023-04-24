package com.khoders.asset.mapper;

import com.khoders.asset.dto.CompanyDto;
import com.khoders.asset.dto.Sql;
import com.khoders.asset.dto.authpayload.JwtResponse;
import com.khoders.asset.dto.authpayload.LoginRequest;
import com.khoders.asset.dto.authpayload.RoleDto;
import com.khoders.asset.dto.authpayload.UserAccountDto;
import com.khoders.asset.exceptions.BadDataException;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.jwt.JwtService;
import com.khoders.asset.repository.RoleRepository;
import com.khoders.asset.repository.UserRepository;
import com.khoders.asset.services.auth.RefreshTokenService;
import com.khoders.entities.Company;
import com.khoders.entities.auth.RefreshToken;
import com.khoders.entities.auth.Role;
import com.khoders.entities.auth.UserAccount;
import com.khoders.entities.constants.UserRole;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import com.khoders.resource.utilities.Stringz;
import com.khoders.resource.utilities.SystemUtils;
import com.khoders.springapi.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class AuthMapper {
    private static final Logger log = LoggerFactory.getLogger(AuthMapper.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private AppService appService;
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
    public UserAccount createAccount(UserAccountDto dto) throws Exception {
        UserAccount user = new UserAccount();
        if (dto.getId() != null) {
            user.setId(dto.getId());
        }
        user.setRefNo(user.getRefNo());

        if (!Stringz.isNotNullOrEmpty(dto.getEmailAddress())) {
            throw new BadDataException("Email cannot be empty");
        }
        user.setEmailAddress(dto.getEmailAddress());
        user.setPrimaryNumber(dto.getPrimaryNumber());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));

        Optional<UserAccount> userAccount = userRepository.findByEmailAddress(dto.getEmailAddress());
        if (userAccount.isPresent()) {
            throw new BadDataException("A user with the email address already exist");
        }
        Set<String> strRoles = dto.getUserRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleName(UserRole.ROLE_USER)
                    .orElseThrow(() -> new DataNotFoundException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = null;
                        try {
                            adminRole = roleRepository.findByRoleName(UserRole.ROLE_ADMIN)
                                    .orElseThrow(() -> new DataNotFoundException("Error: Role is not found."));
                        } catch (DataNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = null;
                        try {
                            modRole = roleRepository.findByRoleName(UserRole.ROLE_MODERATOR)
                                    .orElseThrow(() -> new DataNotFoundException("Error: Role is not found."));
                        } catch (DataNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = null;
                        try {
                            userRole = roleRepository.findByRoleName(UserRole.ROLE_USER)
                                    .orElseThrow(() -> new DataNotFoundException("Error: Role is not found."));
                        } catch (DataNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        return user;
    }

    public UserAccountDto toDto(UserAccount user) {
        UserAccountDto dto = new UserAccountDto();
        if (user == null) {
            return null;
        }
        dto.setId(user.getId());
        dto.setEmailAddress(user.getEmailAddress());
        dto.setPrimaryNumber(user.getPrimaryNumber());
        dto.setValueDate(DateUtil.parseLocalDateString(user.getValueDate(), Pattern.ddMMyyyy));
        return dto;
    }

    public JwtResponse toJwtResponse(LoginRequest loginRequest) throws Exception {
        JwtResponse jwtResponse = new JwtResponse();
        if (loginRequest.getEmailAddress() == null) {
            throw new DataNotFoundException("Please enter email");
        }
        if (loginRequest.getPassword() == null) {
            throw new DataNotFoundException("Please enter password");
        }
        System.out.println("Email Address --- " + loginRequest.getEmailAddress() + "\n");
        System.out.println("Password --- " + loginRequest.getPassword());
//        Authentication authentication = null;
//        try {
//             authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(), loginRequest.getPassword()));
//        } catch (BadCredentialsException e) {
//            System.out.println("Printing........");
//            e.printStackTrace();
//        }
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String jwtToken = jwtService.generateToken(loginRequest.getEmailAddress());
        UserAccount userAccount = userRepository.findByEmailAddress(loginRequest.getEmailAddress()).orElseThrow(() -> new DataNotFoundException("User Not Found"));

        Set<RoleDto> roles = new HashSet<>();
        System.out.println("Roles: "+ SystemUtils.KJson().toJson(userAccount.getRoles()));
        userAccount.getRoles().forEach(item -> {
            RoleDto dto = new RoleDto();
            dto.setId(item.getId());
            dto.setRoleName(item.getRoleName().name());
            roles.add(dto);
        });

        List<Company> companies = companies(userAccount);
        List<CompanyDto> companyList = new LinkedList<>();
        for (Company company : companies) {
            CompanyDto dto = new CompanyDto();
            dto.setId(company.getId());
            dto.setCompanyName(company.getCompanyName());
            dto.setCompanyAddress(company.getCompanyAddress());
            dto.setTelephone(company.getTelephone());
            dto.setWebsite(company.getWebsite());
            companyList.add(dto);
        }

        jwtResponse.setAccessToken(jwtToken);
        jwtResponse.setId(userAccount.getId());
        jwtResponse.setValueDate(DateUtil.parseLocalDateString(userAccount.getValueDate(), Pattern.ddMMyyyy));
        jwtResponse.setRoleList(roles);
        jwtResponse.setCompanyList(companyList);

        System.out.println("start 7 \n");
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userAccount.getId());
        System.out.println("start 8 \n");
        jwtResponse.setExpiryDate(String.valueOf(refreshToken.getExpiryDate()));
        jwtResponse.setRefreshToken(refreshToken.getToken());

        return jwtResponse;
    }

    public List<Company> companies(UserAccount userAccount) {
        SqlParameterSource param = new MapSqlParameterSource(Company._primaryUserId, userAccount.getId());
        return jdbc.query(Sql.COMPANY_BY_USER_ID, param, new RowMapper<Company>() {
            @Override
            public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
                Company company = new Company();
                company.setId(rs.getString("id"));
                company.setCompanyName(rs.getString("company_name"));
                company.setCompanyAddress(rs.getString("company_address"));
                company.setTelephone(rs.getString("telephone"));
                company.setWebsite(rs.getString("website"));
                company.setZipCode(rs.getString("zipcode"));
                return company;
            }
        });
    }
}
