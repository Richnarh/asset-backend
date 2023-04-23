package com.khoders.asset.services;

import com.khoders.entities.maintenance.RequestType;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestTypeService {
    @Autowired
    private AppService appService;

    public RequestType save(RequestType requestType) {
        return appService.save(requestType);
    }

    public List<RequestType> requestTypes() {
        return appService.findAll(RequestType.class);
    }

    public RequestType findById(String requestTypeId) {
        return appService.findById(RequestType.class, requestTypeId);
    }

    public boolean delete(String requestTypeId) throws Exception {
        return appService.deleteById(RequestType.class, requestTypeId);
    }
}
