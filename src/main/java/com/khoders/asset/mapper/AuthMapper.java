package com.khoders.asset.mapper;

import com.khoders.asset.Repository.RoleRepository;
import com.khoders.asset.Repository.UserRepository;
import com.khoders.asset.dto.CompanyDto;
import com.khoders.asset.dto.authpayload.JwtResponse;
import com.khoders.asset.dto.authpayload.LoginRequest;
import com.khoders.asset.dto.authpayload.RoleDto;
import com.khoders.asset.dto.authpayload.UserAccountDto;
import com.khoders.asset.entities.Company;
import com.khoders.asset.entities.auth.RefreshToken;
import com.khoders.asset.entities.auth.Role;
import com.khoders.asset.entities.auth.UserAccount;
import com.khoders.asset.entities.constants.UserRole;
import com.khoders.asset.jwt.JwtService;
import com.khoders.asset.services.auth.AuthService;
import com.khoders.asset.services.auth.RefreshTokenService;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.exception.BadDataException;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import com.khoders.resource.utilities.Stringz;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.BadRequestException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
public class AuthMapper {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtService jwtService;
    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RefreshTokenService refreshTokenService;
    @Autowired private CrudBuilder builder;

    public UserAccount createAccount(UserAccountDto dto) {
        UserAccount user = new UserAccount();
        if (dto.getId() != null) {
            user.setId(dto.getId());
        }
        user.setRefNo(user.getRefNo());

        if(Stringz.isNullOrEmpty(dto.getEmailAddress())){
            throw new BadDataException("Email cannot be null");
        }
        user.setEmailAddress(dto.getEmailAddress());
        user.setPrimaryNumber(dto.getPrimaryNumber());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));

        UserAccount userAccount = builder.simpleFind(UserAccount.class, dto.getEmailAddress());
        if (userAccount != null){
            throw new BadDataException("A user with the email address already exist");
        }
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
        System.out.println("Email Address --- "+loginRequest.getEmailAddress()+"\n");
        System.out.println("Password --- "+loginRequest.getPassword()+"\n");
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
        String jwtToken = jwtService.generateToken(loginRequest.getEmailAddress() );
        UserAccount userAccount = userRepository.findByEmailAddress(loginRequest.getEmailAddress()).orElseThrow(() -> new DataNotFoundException("User Not Found"));

        Set<RoleDto> roles = new HashSet<>();
        userAccount.getRoles().forEach(item ->{
            RoleDto dto = new RoleDto();
            dto.setId(item.getId());
            dto.setRoleName(item.getRoleName().name());
            roles.add(dto);
        });

        List<Company> companies = companies(userAccount);
        List<CompanyDto> companyList = new LinkedList<>();
        for (Company company:companies){
            CompanyDto dto = new CompanyDto();
            dto.setId(company.getId());
            dto.setCompanyName(company.getCompanyName());
            dto.setCompanyType(company.getCompanyType().getLabel());
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

    public List<Company> companies(UserAccount userAccount){
        Session session = builder.session();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Company> criteriaQuery = builder.createQuery(Company.class);
            Root<Company> root = criteriaQuery.from(Company.class);
            criteriaQuery.where(builder.and(builder.equal(root.get("primaryUser"), userAccount)));
            criteriaQuery.orderBy(builder.asc(root.get(Company._companyName)));
            Query<Company> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }
}
