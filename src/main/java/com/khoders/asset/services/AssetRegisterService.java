package com.khoders.asset.services;

import com.khoders.asset.entities.Asset;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
@Service
public class AssetRegisterService {
    @Autowired
    private CrudBuilder builder;

    public Asset save(Asset asset) {
        return builder.save(asset);
    }

    public List<Asset> registers() {
        return builder.findAll(Asset.class);
    }

    public Asset findById(String assetId) {
        return builder.findOne(assetId, Asset.class);
    }

    public boolean delete(String assetId) {
        return builder.deleteById(assetId, Asset.class);
    }
}
