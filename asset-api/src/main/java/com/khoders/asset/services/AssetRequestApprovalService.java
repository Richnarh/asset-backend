package com.khoders.asset.services;

import com.khoders.entities.AssetRequestApproval;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetRequestApprovalService {
    @Autowired
    private AppService appService;

    public AssetRequestApproval save(AssetRequestApproval requestApproval) {
        return appService.save(requestApproval);
    }

    public List<AssetRequestApproval> requestApprovals() {
        return appService.findAll(AssetRequestApproval.class);
    }

    public AssetRequestApproval findById(String requestApprovalId) {
        return appService.findById(AssetRequestApproval.class, requestApprovalId);
    }

    public boolean delete(String requestApprovalId) throws Exception {
        return appService.deleteById(AssetRequestApproval.class, requestApprovalId);
    }
}
