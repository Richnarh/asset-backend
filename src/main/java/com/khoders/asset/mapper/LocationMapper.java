package com.khoders.asset.mapper;

import com.khoders.asset.dto.LocationDto;
import com.khoders.asset.entities.Location;
import com.khoders.resource.spring.CrudBuilder;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LocationMapper {
    private static final Logger log = LoggerFactory.getLogger(LocationMapper.class);
    @Autowired
    private CrudBuilder builder;

    public Location toEntity(LocationDto dto) {
        Location location = new Location();
        if (dto.getId() != null) {
            location.setId(dto.getId());
        }
        location.setLocationName(dto.getLocationName());
        location.setAddress(dto.getAddress());
        location.setRefNo(location.getRefNo());
        if (dto.getValueDate() == null) {
            location.setValueDate(LocalDate.now());
        } else {
            location.setValueDate(DateUtil.parseLocalDate(dto.getValueDate(), Pattern._yyyyMMdd));
        }
        return location;
    }

    public LocationDto toDto(Location location) {
        LocationDto dto = new LocationDto();
        if (location.getId() == null) {
            return null;
        }
        dto.setId(location.getId());
        dto.setLocationName(location.getLocationName());
        dto.setAddress(location.getAddress());
        dto.setValueDate(DateUtil.parseLocalDateString(location.getValueDate(), Pattern.ddMMyyyy));
        return dto;
    }
}
