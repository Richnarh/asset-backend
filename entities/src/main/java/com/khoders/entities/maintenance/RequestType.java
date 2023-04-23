package com.khoders.entities.maintenance;

import com.khoders.entities.Ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "request_type")
public class RequestType extends Ref {
    @Column(name = "request_name")
    private String requestName;

    @Column(name = "description")
    @Lob
    private String description;

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
