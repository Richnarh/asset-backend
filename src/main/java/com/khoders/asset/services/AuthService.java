package com.khoders.asset.services;

import com.khoders.asset.dto.LoginDto;
import com.khoders.asset.entities.UserAccount;
import com.khoders.asset.utils.CrudBuilder;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Repository
@Transactional
public class AuthService {
    @Autowired
    private CrudBuilder builder;

    public UserAccount saveUser(UserAccount userAccount) {
        return builder.save(userAccount);
    }

    public UserAccount findById(String id) {
        return (UserAccount) builder.findOne(id, UserAccount.class);
    }

    public UserAccount doLogin(LoginDto dto) {
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
