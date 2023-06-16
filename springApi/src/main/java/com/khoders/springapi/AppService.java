/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.springapi;

import com.khoders.springapi.spring.SpringBaseModel;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author Pascal
 */
@Service
@Transactional
public class AppService extends HibernateTemplate implements IService {

    public AppService() {
    }

    public AppService(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Autowired
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public <T> boolean deleteById(Class<T> entityClass, Serializable id) {
        if (id == null) {
            return false;
        }
        T entity = findById(entityClass, id);
        delete(entity);
        return true;
    }
    @Override
    public Serializable save(Object o) throws DataAccessException{
        return super.save(o);
    }
    @Override
    public void delete(Object entity) throws DataAccessException {
        super.delete(entity);
    }

    @Override
    public Object load(String entityName, Serializable id) throws DataAccessException {
        return super.load(entityName, id);
    }

    @Override
    public void refresh(final Object entity) throws DataAccessException {
        super.refresh(entity);
    }

    public <T> T save(SpringBaseModel model) throws DataAccessException {
        if (model == null) {
            return null;
        }
        if (model.getCreatedDateTime() == null) {
            model.setCreatedDateTime(LocalDateTime.now());
        }
        if (model.getValueDate() == null) {
            model.setValueDate(LocalDate.now());
        }
        model.setLastModifiedDate(LocalDateTime.now());
        if (model.getId() == null) {
            model.setId(genId());
            save(model);
        } else if (findById(model.getClass(), model.getId()) != null) {
            super.update(model);
        } else {
            save(model);
        }
        return (T) model;
    }

    @Override
    public <T> List<T> findAll(Class<T> t) throws DataAccessException {
        return super.loadAll(t);
    }

    @Override
    public <T> T findById(Class<T> t, Serializable id) throws DataAccessException {
        if (id == null) {
            return null;
        }
        return super.load(t, id);
    }

    public String genId() {
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        boolean uuidStringMatcher = id.matches(".*[a-zA-Z]+.*");
        if (!uuidStringMatcher) {
            Random random = new Random();
            char cha = (char) (random.nextInt(26) + 'a');
            int numToReplace = random.nextInt(9);
            id = id.replaceAll(String.valueOf(numToReplace), String.valueOf(cha));
        }
        return id;
    }

    @Override
    public <T> T findSingleByCriteria(DetachedCriteria criteria) throws DataAccessException {
        return (T) DataAccessUtils.singleResult(findByCriteria(criteria, 0, 1));
    }
    @Override
    public List<?> findByCriteria(final DetachedCriteria criteria, final int firstResult, final int maxResults)throws DataAccessException {
            return super.findByCriteria(criteria, firstResult, maxResults);
    }
    public <T> T findObj(Class<T> clazz,String fieldName, Object fieldValue){
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        criteria.add(Restrictions.eq(fieldName, fieldValue));
        return findSingleByCriteria(criteria);
    }
    @Override
    public long countCriteria(DetachedCriteria criteria) throws DataAccessException {
        return DataAccessUtils.longResult(findByCriteria(criteria));
    }
    @Override
    public List<?> findByCriteria(DetachedCriteria criteria) throws DataAccessException {
            return super.findByCriteria(criteria);
    }
}
