package com.khoders.asset.services;

import com.khoders.asset.entities.AssetDispatchRequest;
import com.khoders.asset.utils.CrudBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
@Service
public class AssetDispatchRequestService {
    private static final Logger log = LoggerFactory.getLogger(AssetDispatchRequestService.class);
    @Autowired
    private CrudBuilder builder;

    public AssetDispatchRequest save(AssetDispatchRequest dispatchRequest) {
        return builder.save(dispatchRequest);
    }

    public List<AssetDispatchRequest> dispatchRequestList() {
        return builder.findAll(AssetDispatchRequest.class);
    }

    public AssetDispatchRequest findById(String dispatchRequestId) {
        return builder.simpleFind(AssetDispatchRequest.class, dispatchRequestId);
    }

    public boolean delete(String dispatchRequestId) throws Exception {
        return builder.deleteById(dispatchRequestId, AssetDispatchRequest.class);
    }
}
