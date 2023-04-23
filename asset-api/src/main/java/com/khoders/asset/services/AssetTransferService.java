package com.khoders.asset.services;

import com.khoders.entities.AssetTransfer;
import com.khoders.springapi.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AssetTransferService {
    private static final Logger log = LoggerFactory.getLogger(AssetTransferService.class);
    @Autowired
    private AppService appService;

    public AssetTransfer saveTransfer(AssetTransfer assetTransfer) {
        return appService.save(assetTransfer);
    }

    public List<AssetTransfer> transferList() {
        return appService.findAll(AssetTransfer.class);
    }

    public AssetTransfer findById(String id) {
        return appService.findById(AssetTransfer.class, id);
    }

    public boolean delete(String transferId) throws Exception {
        return appService.deleteById(AssetTransfer.class, transferId);
    }
}
