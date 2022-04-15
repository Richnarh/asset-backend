package com.khoders.asset.mapper;

import com.khoders.asset.dto.VendorDto;
import com.khoders.asset.entities.Vendor;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.asset.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VendorMapper {
    private static final Logger log = LoggerFactory.getLogger(VendorMapper.class);
    @Autowired
    private CrudBuilder builder;

    public Vendor toEntity(VendorDto dto){
        Vendor vendor = new Vendor();
        if (dto.getId() != null){
            vendor.setId(SpringUtils.stringToUUID(dto.getId()));
        }
        vendor.setRefNo(vendor.getRefNo());
        vendor.setVendorType(dto.getVendorType());
        vendor.setEmailAddress(dto.getEmailAddress());
        vendor.setVendorName(dto.getVendorName());
        vendor.setPhoneNumber(dto.getPhoneNumber());
        vendor.setLastModifiedDate(LocalDateTime.now());
        return vendor;
    }
    public VendorDto toDto(Vendor vendor){
        VendorDto dto = new VendorDto();
        if(vendor.getId() == null){
            return null;
        }
        dto.setVendorName(vendor.getVendorName());
        dto.setVendorType(vendor.getVendorType());
        dto.setEmailAddress(vendor.getEmailAddress());
        dto.setRefNo(vendor.getRefNo());
        dto.setValueDate(vendor.getValueDate());
        return dto;
    }
}
