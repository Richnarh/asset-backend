package com.khoders.asset.services;

import com.khoders.asset.entities.BusinessClient;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessClientService {
    @Autowired
    private CrudBuilder builder;

    public BusinessClient save(BusinessClient businessClient) {
        return builder.save(businessClient);
    }

    public List<BusinessClient> vendors() {
        return builder.findAll(BusinessClient.class);
    }

    public BusinessClient findById(String businessClientId) {
        return builder.findOne(businessClientId, BusinessClient.class);
    }

    public boolean delete(String businessClientId) throws Exception {
        return builder.deleteById(businessClientId, BusinessClient.class);
    }
}
