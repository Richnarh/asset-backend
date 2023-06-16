package com.khoders.asset.services;

import com.khoders.entities.Asset;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetRegisterService {
    @Autowired
    private AppService appService;

    public Asset save(Asset asset) {
        return appService.save(asset);
    }

    public List<Asset> registers() {
        return appService.findAll(Asset.class);
    }

    public Asset findById(String assetId) {
        return appService.findById(Asset.class, assetId);
    }

    public boolean delete(String assetId) throws Exception {
        return appService.deleteById(Asset.class, assetId);
    }
}
