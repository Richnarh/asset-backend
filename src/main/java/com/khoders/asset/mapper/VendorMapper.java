package com.khoders.asset.mapper;

import com.khoders.asset.dto.VendorDto;
import com.khoders.asset.entities.Vendor;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.enums.ClientType;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
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

    public Vendor toEntity(VendorDto dto) {
        Vendor vendor = new Vendor();
        if (dto.getId() != null) {
            vendor.setId(dto.getId());
        }
        vendor.setRefNo(vendor.getRefNo());
        try {
            log.info("Vendor Type: {} ", dto.getVendorType());
            vendor.setVendorType(ClientType.valueOf(dto.getVendorType()));
        } catch (Exception ignored) {
        }
        vendor.setEmailAddress(dto.getEmailAddress());
        vendor.setVendorName(dto.getVendorName());
        vendor.setPhoneNumber(dto.getPhoneNumber());
        vendor.setLastModifiedDate(LocalDateTime.now());
        return vendor;
    }

    public VendorDto toDto(Vendor vendor) {
        VendorDto dto = new VendorDto();
        if (vendor.getId() == null) return null;
        dto.setId(vendor.getId());
        dto.setVendorName(vendor.getVendorName());
        try {
            dto.setVendorType(vendor.getVendorType().getLabel());
        } catch (Exception ignored) {
        }
        dto.setEmailAddress(vendor.getEmailAddress());
        dto.setPhoneNumber(vendor.getPhoneNumber());
        dto.setValueDate(DateUtil.parseLocalDateString(vendor.getValueDate(), Pattern.ddMMyyyy));
        return dto;
    }
}
