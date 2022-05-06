package com.khoders.asset.mapper;

import com.khoders.asset.dto.InventoryDto;
import com.khoders.asset.entities.Employee;
import com.khoders.asset.entities.Inventory;
import com.khoders.asset.entities.Location;
import com.khoders.asset.entities.Vendor;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.spring.CrudBuilder;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InventoryMapper {
    private static final Logger log = LoggerFactory.getLogger(InventoryMapper.class);
    @Autowired
    private CrudBuilder builder;

    public Inventory toEntity(InventoryDto dto){
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
        if (dto.getVendorId() == null) {
            throw new DataNotFoundException("Please Specify Valid VendorId");
        }
        Location location = builder.findOne(dto.getReceivedAtId(), Location.class);
        if (location != null) {
            inventory.setReceivedAt(location);
        }
        Employee employee = builder.findOne(dto.getReceivedById(), Employee.class);
        if (employee != null) {
            inventory.setReceivedBy(employee);
        }
        Vendor vendor = builder.findOne(dto.getVendorId(), Vendor.class);
        if (vendor != null) {
            inventory.setVendor(vendor);
        }
        inventory.setLastModifiedDate(LocalDateTime.now());

        return inventory;
    }

    public InventoryDto toDto(Inventory inventory){
        if (inventory.getId() == null){
            return null;
        }
        InventoryDto dto = new InventoryDto();
        dto.setId(inventory.getId());
        dto.setReceiptNo(inventory.getReceiptNo());
        if (inventory.getReceivedAt() != null){
            dto.setReceivedAtId(inventory.getReceivedAt().getId());
            dto.setReceivedAt(inventory.getReceivedAt().getLocationName());
        }
        if(inventory.getReceivedBy() != null){
            dto.setReceivedById(inventory.getReceivedBy().getId());
            dto.setReceivedByName(inventory.getReceivedBy().getEmailAddress());
        }
        if (inventory.getVendor() != null){
            dto.setVendorId(inventory.getVendor().getId());
            dto.setVendorName(inventory.getVendor().getVendorName());
        }
        dto.setReceivedDate(DateUtil.parseLocalDateString(inventory.getReceivedDate(), Pattern.ddMMyyyy));
        dto.setTotalPayable(inventory.getTotalPayable());
        return dto;
    }
}
