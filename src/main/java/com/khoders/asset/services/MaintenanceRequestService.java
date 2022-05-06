package com.khoders.asset.services;

import com.khoders.asset.entities.maintenance.MaintenanceRequest;
import com.khoders.resource.spring.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
@Repository
@Service
public class MaintenanceRequestService {
    @Autowired
    private CrudBuilder builder;

    public MaintenanceRequest save(MaintenanceRequest maintenanceRequest) {
        return builder.save(maintenanceRequest);
    }
    public List<MaintenanceRequest> maintenanceRequestList(){return builder.findAll(MaintenanceRequest.class);}
    public MaintenanceRequest findById(String requestId) {
        return builder.simpleFind(MaintenanceRequest.class, requestId);
    }
    public boolean delete(String requestId) {
        return builder.deleteById(requestId, MaintenanceRequest.class);
    }
}
