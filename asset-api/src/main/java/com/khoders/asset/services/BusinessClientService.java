package com.khoders.asset.services;

import com.khoders.entities.BusinessClient;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessClientService {
    @Autowired
    private AppService appService;

    public BusinessClient save(BusinessClient businessClient) {
        return appService.save(businessClient);
    }

    public List<BusinessClient> vendors() {
        return appService.findAll(BusinessClient.class);
    }

    public BusinessClient findById(String businessClientId) {
        return appService.findById(BusinessClient.class, businessClientId);
    }

    public boolean delete(String businessClientId) throws Exception {
        return appService.deleteById(BusinessClient.class, businessClientId);
    }
}
