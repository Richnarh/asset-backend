/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khoders.springapi.spring;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author khoders
 */
@MappedSuperclass
public class SpringBaseModel extends SpringEntityModel implements Serializable {

    public static final String _id="id";
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;

    public SpringBaseModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SpringBaseModel other = (SpringBaseModel) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 9;
        hash = 91 * hash + Objects.hashCode(this.id);
        return hash;
    }

}
