package com.khoders.asset.services.auth;

import com.khoders.asset.dto.CompanyDto;
import com.khoders.asset.dto.Sql;
import com.khoders.asset.dto.authpayload.JwtResponse;
import com.khoders.asset.dto.authpayload.LoginRequest;
import com.khoders.asset.dto.authpayload.RoleDto;
import com.khoders.asset.dto.authpayload.UserAccountDto;
import com.khoders.asset.jwt.JwtService;
import com.khoders.asset.mapper.AuthMapper;
import com.khoders.asset.mapper.CompanyMapper;
import com.khoders.asset.repository.UserRepository;
import com.khoders.entities.Company;
import com.khoders.entities.auth.RefreshToken;
import com.khoders.entities.auth.UserAccount;
import com.khoders.entities.auth.VerificationToken;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import com.khoders.springapi.AppService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private AppService appService;
    @Autowired
    private UserRepository repository;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    AuthService() {
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        UserAccount user = repository.findByEmailAddress(emailAddress).orElseThrow(() -> new UsernameNotFoundException("User Not Found with emailAddress: " + emailAddress));
        return new org.springframework.security.core.userdetails.User(user.getEmailAddress(), user.getPassword(), new ArrayList<>());
    }

    public JwtResponse save(UserAccountDto dto) throws Exception {
        Company company = companyMapper.createCompany(dto);
        UserAccount userAccount = authMapper.createAccount(dto);
        userAccount.setCompany(company);
        if (appService.save(userAccount) != null) {
            company.setPrimaryUser(userAccount);
            appService.save(company);
        }
        System.out.println("userAccount -- " + userAccount.getEmailAddress());
        String jwtToken = jwtService.generateToken(userAccount.getEmailAddress());

        return toJwt(userAccount, jwtToken);
    }

    public UserAccount findById(String id) {
        return appService.findById(UserAccount.class, id);
    }

    public UserAccount doLogin(LoginRequest dto) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(UserAccount._emailAddress, dto.getEmailAddress());
        params.addValue(UserAccount._password, dto.getPassword());
        return jdbc.query(Sql.INVOICE_ITEM_BY_INVOICE_ID, params, new ResultSetExtractor<UserAccount>() {

            @Override
            public UserAccount extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    UserAccount account = new UserAccount();
                    account.setId(rs.getString("id"));
                    account.setEmailAddress(rs.getString("email_address"));
                    account.setPrimaryNumber("primary_number");
                    return account;
                }
                return null;
            }

        });
    }

    public JwtResponse toJwt(UserAccount userAccount, String jwtToken) {
        JwtResponse jwtResponse = new JwtResponse();

        if (userAccount == null) {
            throw new BadRequestException("Something went wrong with the system, Contact Developer with server log");
        }
        Set<RoleDto> roles = new HashSet<>();
        userAccount.getRoles().forEach(item -> {
            RoleDto dto = new RoleDto();
            dto.setId(item.getId());
            dto.setRoleName(item.getRoleName().name());
            roles.add(dto);
        });

        List<Company> companies = companies(userAccount);
        System.out.println("companies: "+companies.toString());
        List<CompanyDto> companyList = new LinkedList<>();
        for (Company company : companies) {
            CompanyDto dto = new CompanyDto();
            dto.setId(company.getId());
            dto.setCompanyName(company.getCompanyName());
            dto.setCompanyType(company.getCompanyType().getLabel());
            companyList.add(dto);
        }

        jwtResponse.setAccessToken(jwtToken);
        jwtResponse.setId(userAccount.getId());
        jwtResponse.setCompanyId(userAccount.getCompany().getId());
        jwtResponse.setUserAccountId(userAccount.getId());
        jwtResponse.setValueDate(DateUtil.parseLocalDateString(userAccount.getValueDate(), Pattern.ddMMyyyy));
        jwtResponse.setRoleList(roles);
        jwtResponse.setCompanyList(companyList);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userAccount.getId());
        jwtResponse.setExpiryDate(String.valueOf(refreshToken.getExpiryDate()));
        jwtResponse.setRefreshToken(refreshToken.getToken());

        return jwtResponse;
    }

    public List<Company> companies(UserAccount userAccount) {
        System.out.println("IdValue: "+userAccount.getId());
        SqlParameterSource param = new MapSqlParameterSource(Company._primaryUserId, userAccount.getId());
        return jdbc.query(Sql.COMPANY_BY_USER_ID, param, new RowMapper<Company>() {
            @Override
            public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
                Company company = new Company();
                company.setId(rs.getString("id"));
                company.setCompanyAddress(rs.getString("company_address"));
                company.setWebsite(rs.getString("website"));
                company.setTelephone(rs.getString("telephone"));
                company.setZipCode(rs.getString("zipcode"));
                company.setEmailAddress(rs.getString("email_address"));
                System.out.println("company: "+company.toString());
                return company;
            }
        });
    }
    public void saveUserVerificationToken(UserAccount userAccount, String verificationToken) {
        VerificationToken token = new VerificationToken(verificationToken,userAccount);
        appService.save(token);
    }
    public VerificationToken findByToken(String verifyToken){
        return appService.findObj(VerificationToken.class, VerificationToken._token, verifyToken);
    }
    public String validateToken(String verifyToken) {
        VerificationToken token = findByToken(verifyToken);
        if(token == null){
            return "Invalid verification token";
        }
        UserAccount user = token.getUserAccount();
        Calendar calendar = Calendar.getInstance();
        if((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            appService.delete(token);
            return "Token already expired";
        }
        user.setEnabled(true);
        appService.save(user);
        System.out.println("Saved Verification_______");
        return "valid";
    }
}
