package com.khoders.asset.services;

import com.khoders.entities.AssetDispatchRequest;
import com.khoders.springapi.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetDispatchRequestService {
    private static final Logger log = LoggerFactory.getLogger(AssetDispatchRequestService.class);
    @Autowired
    private AppService appService;

    public AssetDispatchRequest save(AssetDispatchRequest dispatchRequest) {
        return appService.save(dispatchRequest);
    }

    public List<AssetDispatchRequest> dispatchRequestList() {
        return appService.findAll(AssetDispatchRequest.class);
    }

    public AssetDispatchRequest findById(String dispatchRequestId) {
        return appService.findById(AssetDispatchRequest.class, dispatchRequestId);
    }

    public boolean delete(String dispatchRequestId) throws Exception {
        return appService.deleteById(AssetDispatchRequest.class, dispatchRequestId);
    }
}
