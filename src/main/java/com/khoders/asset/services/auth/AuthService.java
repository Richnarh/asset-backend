package com.khoders.asset.services.auth;

import com.khoders.asset.Repository.UserRepository;
import com.khoders.asset.dto.CompanyDto;
import com.khoders.asset.dto.authpayload.JwtResponse;
import com.khoders.asset.dto.authpayload.LoginRequest;
import com.khoders.asset.dto.authpayload.RoleDto;
import com.khoders.asset.dto.authpayload.UserAccountDto;
import com.khoders.asset.entities.Company;
import com.khoders.asset.entities.auth.RefreshToken;
import com.khoders.asset.entities.auth.UserAccount;
import com.khoders.asset.jwt.JwtService;
import com.khoders.asset.mapper.AuthMapper;
import com.khoders.asset.mapper.CompanyMapper;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.util.*;

@Service
@Transactional
public class AuthService implements UserDetailsService {
    @Autowired private CrudBuilder builder;
    @Autowired private UserRepository repository;
    @Autowired private RefreshTokenService refreshTokenService;
    @Autowired private CompanyMapper companyMapper;
    @Autowired private AuthMapper authMapper;
    @Autowired private JwtService jwtService;

    AuthService(){}
    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        UserAccount user = repository.findByEmailAddress(emailAddress).orElseThrow(() -> new UsernameNotFoundException("User Not Found with emailAddress: " + emailAddress));
        return new org.springframework.security.core.userdetails.User(user.getEmailAddress(), user.getPassword(), new ArrayList<>());
    }

    public JwtResponse save(UserAccountDto dto) {
        Company company = companyMapper.createCompany(dto);
        UserAccount userAccount = authMapper.createAccount(dto);
        userAccount.setCompany(company);
        if(builder.save(userAccount) != null){
            company.setPrimaryUser(userAccount);
            builder.save(company);
        }
        System.out.println("userAccount -- "+userAccount.getEmailAddress());
        String jwtToken = jwtService.generateToken(userAccount.getEmailAddress());

        return toJwt(userAccount,jwtToken);
    }

    public UserAccount findById(String id) {
        return (UserAccount) builder.findOne(id, UserAccount.class);
    }

    public UserAccount doLogin(LoginRequest dto) {
        Session session = builder.session();
        try {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<UserAccount> criteriaQuery = builder.createQuery(UserAccount.class);
            Root<UserAccount> root = criteriaQuery.from(UserAccount.class);
            criteriaQuery.where(builder.and(builder.equal(root.get("emailAddress"), dto.getEmailAddress()), builder.equal(root.get("password"), new BCryptPasswordEncoder().encode(dto.getPassword()))));
            Query<UserAccount> query = session.createQuery(criteriaQuery);
            return query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JwtResponse toJwt(UserAccount userAccount, String jwtToken){
        JwtResponse jwtResponse = new JwtResponse();

        if (userAccount == null){
            throw new BadRequestException("Something went wrong with the system, Contact Developer with server log");
        }
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

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userAccount.getId());
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
            return Collections.emptyList();
        }
    }
}
