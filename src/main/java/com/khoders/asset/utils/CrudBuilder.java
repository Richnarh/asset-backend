package com.khoders.asset.utils;

import com.khoders.asset.entities.AssetDispatchRequest;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
@Repository
public class CrudBuilder{
    @Autowired
    private EntityManager em;

    public <T> T save(T t) {
        try {
            session().saveOrUpdate(t);
            return t;
        }catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T findOne(String id, Class<T> clazz){
        try {
            CriteriaBuilder criteriaBuilder = session().getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
            Root<T> root = criteriaQuery.from(clazz);
            criteriaQuery.where(criteriaBuilder.equal(root.get("id"), SpringUtils.stringToUUID(id)));
            Query<?> query = session().createQuery(criteriaQuery);
            return (T) query.getSingleResult();
        }catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> List<T>  findAll(Class<T> clazz){
        try {
            CriteriaBuilder builder = session().getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
            Root<T> root = criteriaQuery.from(clazz);
            criteriaQuery.select(root);
            Query<?> query = session().createQuery(criteriaQuery);

            return (List<T>) query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public <T> boolean deleteById(String id, Class<T> clazz) {
        try {
            Object obj = findOne(id, clazz);
            if (obj != null) {
                session().delete(obj);
            }
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Session session(){
        return em.unwrap(Session.class);
    }
}
