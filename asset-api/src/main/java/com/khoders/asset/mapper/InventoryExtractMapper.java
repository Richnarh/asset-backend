package com.khoders.asset.mapper;

import com.khoders.asset.dto.InventoryDto;
import com.khoders.asset.dto.InventoryItemDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.entities.*;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class InventoryExtractMapper {
    @Autowired
    private AppService appService;

    public Inventory toEntity(InventoryDto dto) throws Exception {
        Inventory inventory = new Inventory();
        if (dto.getId() != null) {
            inventory.setId(dto.getId());
        }
        inventory.setRefNo(inventory.getRefNo());
        inventory.setReceiptNo(dto.getReceiptNo());
        inventory.setReceivedDate(DateUtil.parseLocalDate(dto.getReceivedDate(), Pattern._yyyyMMdd));
        if (dto.getReceivedAtId() == null) {
            throw new DataNotFoundException("Please Specify Valid ReceivedAtId (locationId)");
        }
        if (dto.getReceivedById() == null) {
            throw new DataNotFoundException("Please Specify Valid ReceivedById (employeeId)");
        }
        if (dto.getBusinessClientId() == null) {
            throw new DataNotFoundException("Please Specify Valid VendorId");
        }
        Location location = appService.findById(Location.class, dto.getReceivedAtId());
        if (location != null) {
            inventory.setReceivedAt(location);
        }
        Employee employee = appService.findById(Employee.class, dto.getReceivedById());
        if (employee != null) {
            inventory.setReceivedBy(employee);
        }
        BusinessClient businessClient = appService.findById(BusinessClient.class, dto.getBusinessClientId());
        if (businessClient != null) {
            inventory.setBusinessClient(businessClient);
        }
        inventory.setInventoryItemList(toEntity(dto.getInventoryItemList()));

        return inventory;
    }

    public List<InventoryItem> toEntity(List<InventoryItemDto> itemDtoList) throws Exception {
        List<InventoryItem> inventoryItemList = new LinkedList<>();
        for (InventoryItemDto dto : itemDtoList) {
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
            Category category = appService.findById(Category.class, dto.getProductCategoryId());
            if (category != null) {
                inventoryItem.setProductCategory(category);
            }
            Inventory inventory = appService.findById(Inventory.class, dto.getInventory());
            if (inventory != null) {
                inventoryItem.setInventory(inventory);
            }
            inventoryItemList.add(inventoryItem);
        }
        return inventoryItemList;
    }


    public InventoryDto toDto(Inventory inventory) {
        if (inventory.getId() == null) {
            return null;
        }
        InventoryDto dto = new InventoryDto();
        dto.setId(inventory.getId());
        dto.setReceiptNo(inventory.getReceiptNo());
        if (inventory.getReceivedAt() != null) {
            dto.setReceivedAtId(inventory.getReceivedAt().getId());
            dto.setReceivedAt(inventory.getReceivedAt().getLocationName());
        }
        if (inventory.getReceivedBy() != null) {
            dto.setReceivedById(inventory.getReceivedBy().getId());
            dto.setReceivedByName(inventory.getReceivedBy().getEmailAddress());
        }
        if (inventory.getBusinessClient() != null) {
            dto.setBusinessClientId(inventory.getBusinessClient().getId());
            dto.setBusinessClientName(inventory.getBusinessClient().getEmailAddress());
        }
        dto.setReceivedDate(DateUtil.parseLocalDateString(inventory.getReceivedDate(), Pattern.ddMMyyyy));
        dto.setTotalPayable(inventory.getTotalPayable());
        return dto;
    }
}
