package com.khoders.asset.mapper;

import com.khoders.asset.dto.BusinessClientDto;
import com.khoders.asset.entities.BusinessClient;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.enums.ClientType;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VendorMapper {
    private static final Logger log = LoggerFactory.getLogger(VendorMapper.class);
    @Autowired
    private CrudBuilder builder;

    public BusinessClient toEntity(BusinessClientDto dto) {
        BusinessClient businessClient = new BusinessClient();
        if (dto.getId() != null) {
            businessClient.setId(dto.getId());
        }
        businessClient.setRefNo(businessClient.getRefNo());
        try {
            log.info("Vendor Type: {} ", dto.getVendorType());
            businessClient.setClientType(ClientType.valueOf(dto.getVendorType()));
        } catch (Exception ignored) {
        }
        businessClient.setEmailAddress(dto.getEmailAddress());
        businessClient.setFirstname(dto.getVendorName());
        businessClient.setPhoneNumber(dto.getPhoneNumber());
        return businessClient;
    }

    public BusinessClientDto toDto(BusinessClient businessClient) {
        BusinessClientDto dto = new BusinessClientDto();
        if (businessClient.getId() == null) return null;
        dto.setId(businessClient.getId());
        dto.setVendorName(businessClient.getFirstname());
        try {
            dto.setVendorType(businessClient.getClientType().getLabel());
        } catch (Exception ignored) {
        }
        dto.setEmailAddress(businessClient.getEmailAddress());
        dto.setPhoneNumber(businessClient.getPhoneNumber());
        dto.setValueDate(DateUtil.parseLocalDateString(businessClient.getValueDate(), Pattern.ddMMyyyy));
        return dto;
    }
}
