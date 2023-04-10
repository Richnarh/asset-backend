package com.khoders.asset.services;

import com.khoders.asset.entities.InventoryItem;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public boolean delete(String inventoryItemId) throws Exception {
        return builder.deleteById(inventoryItemId, InventoryItem.class);
    }
}
