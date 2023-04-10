package com.khoders.asset.services;

import com.khoders.asset.entities.maintenance.RequestType;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public boolean delete(String requestTypeId) throws Exception {
        return builder.deleteById(requestTypeId, RequestType.class);
    }
}
