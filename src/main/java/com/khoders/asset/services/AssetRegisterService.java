package com.khoders.asset.services;

import com.khoders.asset.entities.AssetRegister;
import com.khoders.asset.entities.Location;
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
    @Autowired private CrudBuilder builder;

    public AssetRegister save(AssetRegister assetRegister){
        return builder.save(assetRegister);
    }
    public List<AssetRegister> registers(){
        return builder.findAll(AssetRegister.class);
    }
    public AssetRegister findById(String assetRegisterId){
        return builder.findOne(assetRegisterId,AssetRegister.class);
    }
    public boolean delete(String assetRegisterId){
        return builder.deleteById(assetRegisterId, AssetRegister.class);
    }
}
