package com.khoders.asset.services;

import com.khoders.asset.entities.BusinessClient;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
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

    public boolean delete(String businessClientId) {
        return builder.deleteById(businessClientId, BusinessClient.class);
    }
}
