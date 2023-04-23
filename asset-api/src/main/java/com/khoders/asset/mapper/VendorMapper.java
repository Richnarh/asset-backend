package com.khoders.asset.mapper;

import com.khoders.asset.dto.BusinessClientDto;
import com.khoders.entities.BusinessClient;
import com.khoders.resource.enums.ClientType;
import com.khoders.resource.enums.Title;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import org.springframework.stereotype.Component;

@Component
public class VendorMapper {

    public BusinessClient toEntity(BusinessClientDto dto) {
        BusinessClient businessClient = new BusinessClient();
        if (dto.getId() != null) {
            businessClient.setId(dto.getId());
        }
        businessClient.setRefNo(businessClient.getRefNo());
        try {
            businessClient.setClientType(ClientType.valueOf(dto.getClientType()));
        } catch (Exception ignored) {
        }
        try {
            businessClient.setTitle(Title.valueOf(dto.getTitle()));
        } catch (Exception ignored) {
        }
        businessClient.setEmailAddress(dto.getEmailAddress());
        businessClient.setFirstname(dto.getFirstname());
        businessClient.setLastname(dto.getLastname());
        businessClient.setPhoneNumber(dto.getPhoneNumber());
        businessClient.setCompanyName(dto.getCompanyName());
        businessClient.setZipCode(dto.getZipCode());
        businessClient.setSocialMediaUrl(dto.getSocialMediaUrl());
        businessClient.setAddress(dto.getAddress());
        return businessClient;
    }

    public BusinessClientDto toDto(BusinessClient businessClient) {
        BusinessClientDto dto = new BusinessClientDto();
        if (businessClient.getId() == null) return null;
        dto.setId(businessClient.getId());
        dto.setFirstname(businessClient.getFirstname());
        dto.setLastname(businessClient.getLastname());
        try {
            dto.setClientType(businessClient.getClientType().getLabel());
        } catch (Exception ignored) {
        }
        try {
            dto.setTitle(businessClient.getTitle().getLabel());
        } catch (Exception ignored) {
        }
        dto.setEmailAddress(businessClient.getEmailAddress());
        dto.setPhoneNumber(businessClient.getPhoneNumber());
        dto.setAddress(businessClient.getAddress());
        dto.setZipCode(businessClient.getZipCode());
        dto.setCompanyName(businessClient.getCompanyName());
        dto.setSocialMediaUrl(businessClient.getSocialMediaUrl());
        dto.setValueDate(DateUtil.parseLocalDateString(businessClient.getValueDate(), Pattern.ddMMyyyy));
        return dto;
    }
}
