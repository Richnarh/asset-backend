package com.khoders.asset.services;

import com.khoders.asset.entities.InventoryItem;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
@Service
public class InventoryItemService {
    @Autowired
    private CrudBuilder builder;

    public InventoryItem save(InventoryItem inventoryItem) {
        return builder.save(inventoryItem);
    }

    public List<InventoryItem> inventoryItemList() {
        return builder.findAll(InventoryItem.class);
    }

    public InventoryItem findById(String inventoryItemId) {
        return builder.simpleFind(InventoryItem.class, inventoryItemId);
    }

    public boolean delete(String inventoryItemId) {
        return builder.deleteById(inventoryItemId, InventoryItem.class);
    }
}
