package com.khoders.asset.utils;

import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.resource.spring.SpringBaseModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Transactional
@Service
@Repository
public class CrudBuilder {
    private final Logger log = LoggerFactory.getLogger(CrudBuilder.class);
    @Autowired
    private EntityManager em;

    public String genId() {
        try {
            String id = UUID.randomUUID().toString().replaceAll("-", "");
            try {
                boolean uuidStringMatcher = id.matches(".*[a-zA-Z]+.*");
                if (!uuidStringMatcher) {
                    Random random = new Random();
                    char cha = (char) (random.nextInt(26) + 'a');
                    int numToReplace = random.nextInt(9);
                    id = id.replaceAll(String.valueOf(numToReplace), String.valueOf(cha));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public <T> T save(SpringBaseModel model) {
        try {
            if (model == null) return null;
            if (model.getCreatedDateTime() == null) {
                model.setCreatedDateTime(LocalDateTime.now());
            }
            if (model.getValueDate() == null) {
                model.setValueDate(LocalDate.now());
            }
            model.setLastModifiedDate(LocalDateTime.now());
            if (model.getId() == null) {
                model.setId(genId());
                session().persist(model);
            } else
                if (simpleFind(model.getClass(), model.getId()) != null) {
                    session().merge(model);
                } else {
                    session().persist(model);
                }
            return (T) model;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T findOne(String id, Class<T> clazz) {
        try {
            if (id == null) return null;
            CriteriaBuilder criteriaBuilder = session().getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
            Root<T> root = criteriaQuery.from(clazz);
            criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
            Query<?> query = session().createQuery(criteriaQuery);
            return (T) query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return (T) Collections.emptyList();
    }

    public <T> T simpleFind(Class<T> t, String id) {
        return session().find(t, id);
    }

    public <T> List<T> findAll(Class<T> clazz) {
        try {
            CriteriaBuilder builder = session().getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
            Root<T> root = criteriaQuery.from(clazz);
            criteriaQuery.select(root);
            Query<?> query = session().createQuery(criteriaQuery);
            return (List<T>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public <T> boolean deleteById(String id, Class<T> clazz) {
        try {
            if (id == null) return false;
            Object obj = session().find(clazz, id);
            log.debug("Object Log: {} ", SpringUtils.KJson().toJson(obj));
            if (obj != null) {
                session().delete(obj);
                return true;
            } else {
                throw new DataNotFoundException("No record to delete for ID: " + id);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(SpringBaseModel springBaseModel) {
        if (springBaseModel == null) {
            return false;
        }
        try {
            session().remove(em.merge(springBaseModel));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public <T> T persist(Object o) {
        if (o == null) {
            return null;
        }
        beginTransaction();
        session().merge(o);
        commit();
        return (T) o;
    }

    public void beginTransaction() {
        try {
            session().getTransaction().begin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void commit() {
        try {
            session().flush();
            session().getTransaction().commit();
            session().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Session session() {
        return em.unwrap(Session.class);
    }
}
