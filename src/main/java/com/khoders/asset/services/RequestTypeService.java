package com.khoders.asset.services;

import com.khoders.asset.entities.Department;
import com.khoders.asset.entities.maintenance.RequestType;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
@Service
public class RequestTypeService {
    @Autowired
    private CrudBuilder builder;

    public RequestType save(RequestType requestType) {
        return builder.save(requestType);
    }

    public List<RequestType> requestTypes() {
        return builder.findAll(RequestType.class);
    }

    public RequestType findById(String requestTypeId) {
        return builder.findOne(requestTypeId, RequestType.class);
    }

    public boolean delete(String requestTypeId) {
        return builder.deleteById(requestTypeId, RequestType.class);
    }
}
