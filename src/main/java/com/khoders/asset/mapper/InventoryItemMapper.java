package com.khoders.asset.mapper;

import com.khoders.asset.dto.InventoryItemDto;
import com.khoders.asset.entities.InventoryItem;
import com.khoders.asset.utils.CrudBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryItemMapper {
    private static final Logger log = LoggerFactory.getLogger(InventoryItemMapper.class);
    @Autowired
    private CrudBuilder builder;

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
