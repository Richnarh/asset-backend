package com.khoders.asset.services;

import com.khoders.asset.dto.maintenance.MaintenanceTaskDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.mapper.maintenance.MaintenanceTaskMapper;
import com.khoders.entities.maintenance.MaintenanceTask;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MaintenanceTaskService {
    @Autowired
    private AppService appService;
    @Autowired
    private MaintenanceTaskMapper taskMapper;

    public MaintenanceTaskDto toEntity(MaintenanceTaskDto dto) throws Exception {
        if (dto.getId() != null) {
            MaintenanceTask occurrence = appService.findById(MaintenanceTask.class, dto.getId());
            if (occurrence == null) {
                throw new DataNotFoundException("MaintenanceTask with ID: " + dto.getId() + " Not Found");
            }
        }
        MaintenanceTask maintenanceTask = taskMapper.toEntity(dto);
        if (appService.save(maintenanceTask) != null) {
            return taskMapper.toDto(maintenanceTask);
        }
        return null;
    }

    public List<MaintenanceTaskDto> maintenanceTaskList() {
        List<MaintenanceTaskDto> dtoList = new LinkedList<>();
        List<MaintenanceTask> maintenanceTaskList = appService.findAll(MaintenanceTask.class);
        for (MaintenanceTask maintenanceTask : maintenanceTaskList) {
            dtoList.add(taskMapper.toDto(maintenanceTask));
        }
        return dtoList;
    }

    public MaintenanceTaskDto findById(String maintenanceTaskId) {
        return taskMapper.toDto(appService.findById(MaintenanceTask.class, maintenanceTaskId));
    }

    public boolean delete(String maintenanceTaskId) throws Exception {
        return appService.deleteById(MaintenanceTask.class, maintenanceTaskId);
    }
}
