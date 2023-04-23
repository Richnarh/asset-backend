package com.khoders.asset.services;

import com.khoders.entities.InventoryItem;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryItemService {
    @Autowired
    private AppService appService;

    public InventoryItem save(InventoryItem inventoryItem) {
        return appService.save(inventoryItem);
    }

    public List<InventoryItem> inventoryItemList() {
        return appService.findAll(InventoryItem.class);
    }

    public InventoryItem findById(String inventoryItemId) {
        return appService.findById(InventoryItem.class, inventoryItemId);
    }

    public boolean delete(String inventoryItemId) throws Exception {
        return appService.deleteById(InventoryItem.class, inventoryItemId);
    }
}
