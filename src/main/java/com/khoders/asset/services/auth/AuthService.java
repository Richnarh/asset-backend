package com.khoders.asset.services.auth;

import com.khoders.asset.Repository.UserRepository;
import com.khoders.asset.dto.authpayload.LoginRequest;
import com.khoders.asset.entities.auth.UserAccount;
import com.khoders.resource.spring.CrudBuilder;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;

@Repository
@Transactional
public class AuthService implements UserDetailsService {
    @Autowired
    private CrudBuilder builder;
    @Autowired private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        UserAccount user = repository.findByEmailAddress(emailAddress).orElseThrow(() -> new UsernameNotFoundException("User Not Found with emailAddress: " + emailAddress));
        return new org.springframework.security.core.userdetails.User(user.getEmailAddress(), user.getPassword(), new ArrayList<>());
    }

    public UserAccount saveUser(UserAccount userAccount) {
        return builder.save(userAccount);
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
}
