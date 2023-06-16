package com.khoders.asset.mapper.maintenance;

import com.khoders.asset.dto.maintenance.MaintenanceTaskDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.entities.constants.Status;
import com.khoders.entities.constants.TaskPriority;
import com.khoders.entities.maintenance.MaintenanceTask;
import com.khoders.entities.maintenance.RequestType;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceTaskMapper {
    @Autowired
    private AppService appService;

    public MaintenanceTask toEntity(MaintenanceTaskDto dto) throws Exception {
        MaintenanceTask task = new MaintenanceTask();
        if (dto.getId() != null) {
            task.setId(dto.getId());
        }
        task.setTaskName(dto.getTaskName());
        task.setStartDate(DateUtil.parseLocalDate(dto.getStartDate(), Pattern._yyyyMMdd));
        try {
            task.setTaskStatus(Status.valueOf(dto.getTaskStatus()));
        } catch (Exception ignored) {
        }
        try {
            task.setTaskPriority(TaskPriority.valueOf(dto.getTaskPriority()));
        } catch (Exception ignored) {
        }

        if (dto.getRequestTypeId() == null) {
            throw new DataNotFoundException("Please Specify Valid RequestType");
        }
        RequestType requestType = appService.findById(RequestType.class, dto.getRequestTypeId());
        if (requestType != null) {
            task.setRequestType(requestType);
        }
        return task;
    }

    public MaintenanceTaskDto toDto(MaintenanceTask task) {
        MaintenanceTaskDto dto = new MaintenanceTaskDto();
        if (task.getId() == null) return null;
        dto.setId(task.getId());
        dto.setTaskPriority(task.getTaskPriority().getLabel());
        dto.setTaskName(task.getTaskName());
        dto.setTaskStatus(task.getTaskStatus().getLabel());
        dto.setStartDate(DateUtil.parseLocalDateString(task.getStartDate(), Pattern.ddMMyyyy));
        if (task.getRequestType() != null) {
            dto.setRequestTypeId(task.getRequestType().getId());
            dto.setRequestTypeName(task.getRequestType().getRequestName());
        }
        return dto;
    }
}
