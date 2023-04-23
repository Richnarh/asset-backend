package com.khoders.asset.mapper.maintenance;

import com.khoders.asset.dto.maintenance.RequestTypeDto;
import com.khoders.entities.maintenance.RequestType;
import org.springframework.stereotype.Component;

@Component
public class RequestTypeMapper {

    public RequestType toEntity(RequestTypeDto dto) {
        RequestType requestType = new RequestType();
        if (dto.getId() != null) {
            requestType.setId(dto.getId());
        }
        requestType.setRequestName(dto.getRequestName());
        requestType.setDescription(dto.getDescription());
        return requestType;
    }

    public RequestTypeDto toDto(RequestType requestType) {
        RequestTypeDto dto = new RequestTypeDto();
        if (requestType.getId() == null) return null;
        dto.setId(requestType.getId());
        dto.setRequestName(requestType.getRequestName());
        dto.setDescription(requestType.getDescription());
        return dto;
    }
}
