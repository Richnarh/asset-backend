package com.khoders.asset.mapper;

import com.khoders.asset.dto.LocationDto;
import com.khoders.asset.entities.Location;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.asset.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocationMapper {
    private static final Logger log = LoggerFactory.getLogger(LocationMapper.class);
    @Autowired
    private CrudBuilder builder;

    public Location toEntity(LocationDto dto){
        Location location = new Location();
        if (dto.getId() != null){
            location.setId(SpringUtils.stringToUUID(dto.getId()));
        }
        location.setLocationName(dto.getLocationName());
        location.setAddress(dto.getAddress());
        location.setRefNo(location.getRefNo());
        location.setLastModifiedDate(LocalDateTime.now());
        return location;
    }
    public LocationDto toDto(Location location){
        LocationDto dto = new LocationDto();
        if (dto.getId() != null){
            dto.setId(SpringUtils.UUIDtoString(location.getId()));
        }
        dto.setLocationName(location.getLocationName());
        dto.setAddress(location.getAddress());
        dto.setRefNo(location.getRefNo());
        return dto;
    }
}
