package com.khoders.asset.services;

import com.khoders.entities.maintenance.MaintenanceRequest;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceRequestService {
    @Autowired
    private AppService appService;

    public MaintenanceRequest save(MaintenanceRequest maintenanceRequest) {
        return appService.save(maintenanceRequest);
    }
    public List<MaintenanceRequest> maintenanceRequestList() {
        return appService.findAll(MaintenanceRequest.class);
    }

    public MaintenanceRequest findById(String requestId) {
        return appService.findById(MaintenanceRequest.class, requestId);
    }

    public boolean delete(String requestId) throws Exception {
        return appService.deleteById(MaintenanceRequest.class, requestId);
    }
}
