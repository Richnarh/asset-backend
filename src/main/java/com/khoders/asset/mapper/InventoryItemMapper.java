package com.khoders.asset.mapper;

import com.khoders.asset.dto.InventoryItemDto;
import com.khoders.asset.entities.Category;
import com.khoders.asset.entities.Inventory;
import com.khoders.asset.entities.InventoryItem;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.spring.CrudBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryItemMapper {
    private static final Logger log = LoggerFactory.getLogger(InventoryItemMapper.class);
    @Autowired
    private CrudBuilder builder;

    public InventoryItem toEntity(InventoryItemDto dto){
        InventoryItem inventoryItem = new InventoryItem();
        if (dto.getId() != null) {
            inventoryItem.setId(dto.getId());
        }
        inventoryItem.setRefNo(inventoryItem.getRefNo());
        inventoryItem.setProductName(dto.getProductName());
        inventoryItem.setQuantity(dto.getQuantity());
        inventoryItem.setSerialNumber(dto.getSerialNumber());
        inventoryItem.setTotalAmount(dto.getTotalAmount());
        if (dto.getProductCategoryId() == null) {
            throw new DataNotFoundException("Please Specify Valid ProductCategoryId");
        }
        if (dto.getInventory() == null) {
            throw new DataNotFoundException("Please Specify Valid InventoryId");
        }
        Category category = builder.findOne(dto.getProductCategoryId(), Category.class);
        if (category != null) {
            inventoryItem.setProductCategory(category);
        }
        Inventory inventory = builder.findOne(dto.getInventory(), Inventory.class);
        if (inventory != null) {
            inventoryItem.setInventory(inventory);
        }
        return inventoryItem;
    }

    public InventoryItemDto toDto(InventoryItem inventoryItem) {
        InventoryItemDto dto = new InventoryItemDto();
        if (inventoryItem.getId() == null) return null;
        dto.setId(inventoryItem.getId());
        dto.setQuantity(inventoryItem.getQuantity());
        dto.setSerialNumber(inventoryItem.getSerialNumber());
        dto.setProductName(inventoryItem.getProductName());
        dto.setTotalAmount(inventoryItem.getTotalAmount());
        if (inventoryItem.getInventory() != null) {
            dto.setInventoryId(inventoryItem.getInventory().getId());
        }
        if (inventoryItem.getProductCategory() != null) {
            dto.setProductCategoryId(inventoryItem.getProductCategory().getId());
            dto.setProductCategoryName(inventoryItem.getProductCategory().getCategoryName());
        }
        return dto;
    }
}
