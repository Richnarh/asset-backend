/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.springapi;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate5.HibernateOperations;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Pascal
 */
public interface IService extends HibernateOperations{
    <T> boolean deleteById(Class<T> t, Serializable id);
    <T>List<T> findAll(Class<T> t);
    <T> T findById(Class<T> t, Serializable id);
    <T> T findSingleByCriteria(DetachedCriteria criteria);
    long countCriteria(DetachedCriteria criteria);
}
