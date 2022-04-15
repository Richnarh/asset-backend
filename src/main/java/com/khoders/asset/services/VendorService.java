package com.khoders.asset.services;

import com.khoders.asset.entities.Vendor;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
@Service
public class VendorService {
    @Autowired private CrudBuilder builder;

    public Vendor save(Vendor vendor){
        return builder.save(vendor);
    }
    public List<Vendor> vendors(){
        return builder.findAll(Vendor.class);
    }
    public Vendor findById(String vendorId){
        return builder.findOne(vendorId,Vendor.class);
    }
    public boolean delete(String vendorId){
        return builder.deleteById(vendorId,Vendor.class);
    }
}
