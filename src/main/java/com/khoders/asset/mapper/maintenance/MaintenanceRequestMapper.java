package com.khoders.asset.mapper.maintenance;

import com.khoders.asset.dto.maintenance.MaintenanceRequestDto;
import com.khoders.asset.entities.Employee;
import com.khoders.asset.entities.constants.Status;
import com.khoders.asset.entities.maintenance.MaintenanceRequest;
import com.khoders.asset.entities.maintenance.RequestType;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceRequestMapper {
    @Autowired
    private CrudBuilder builder;

    public MaintenanceRequest toEntity(MaintenanceRequestDto dto) {
        MaintenanceRequest request = new MaintenanceRequest();
        if (dto.getId() != null) {
            request.setId(dto.getId());
        }
        try {
            request.setRequestStatus(Status.valueOf(dto.getRequestStatus()));
        } catch (Exception ignored) {
        }
        request.setRequestTitle(dto.getRequestTitle());
        request.setDueDate(DateUtil.parseLocalDate(dto.getDueDate(), Pattern._yyyyMMdd));
        request.setRefNo(request.getRefNo());
        if (dto.getRequestTypeId() == null || dto.getRequestTypeId().equals("")) {
            throw new DataNotFoundException("Specify Valid RequestTypeId");
        }
        if (dto.getAssignedUserId() == null || dto.getAssignedUserId().equals("")) {
            throw new DataNotFoundException("Specify Valid AssignedUserId");
        }
        RequestType requestType = builder.simpleFind(RequestType.class, dto.getRequestTypeId());
        if (requestType != null) {
            request.setRequestType(requestType);
        }
        Employee employee = builder.simpleFind(Employee.class, dto.getAssignedUserId());
        if (requestType != null) {
            request.setAssignedUser(employee);
        }
        return request;
    }

    public MaintenanceRequestDto toDto(MaintenanceRequest request) {
        MaintenanceRequestDto dto = new MaintenanceRequestDto();
        if (request.getId() == null) return null;
        dto.setId(request.getId());
        dto.setDueDate(DateUtil.parseLocalDateString(request.getDueDate(), Pattern.ddMMyyyy));
        dto.setRequestStatus(request.getRequestStatus().getLabel());
        dto.setRequestTitle(request.getRequestTitle());
        if (request.getRequestType() != null) {
            dto.setRequestTypeId(request.getRequestType().getId());
            dto.setRequestType(request.getRequestType().getRequestName());
        }
        if (request.getAssignedUser() != null) {
            dto.setAssignedUserId(request.getAssignedUser().getId());
            dto.setAssignedUser(request.getAssignedUser().getEmailAddress());
        }
        return dto;
    }
}
