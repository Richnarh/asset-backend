package com.khoders.asset.mapper;

import com.khoders.asset.dto.InventoryItemDto;
import com.khoders.entities.InventoryItem;
import org.springframework.stereotype.Component;

@Component
public class InventoryItemMapper {
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
