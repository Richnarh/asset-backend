package com.khoders.asset.services;

import com.khoders.asset.entities.Inventory;
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
public class InventoryService {
    @Autowired private CrudBuilder builder;

    public Inventory save(Inventory inventory){
        return builder.save(inventory);
    }
    public List<Inventory> inventories(){
        return builder.findAll(Inventory.class);
    }
    public Inventory findById(String inventoryId){
        return builder.findOne(inventoryId,Inventory.class);
    }
    public boolean delete(String inventoryId){
        return builder.deleteById(inventoryId,Inventory.class);
    }
}
