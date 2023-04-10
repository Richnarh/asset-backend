package com.khoders.asset.services;

import com.khoders.asset.dto.maintenance.MaintenanceTaskDto;
import com.khoders.asset.entities.maintenance.MaintenanceTask;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.mapper.maintenance.MaintenanceTaskMapper;
import com.khoders.asset.utils.CrudBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MaintenanceTaskService {
    @Autowired
    private CrudBuilder builder;
    @Autowired private MaintenanceTaskMapper taskMapper;

    public MaintenanceTaskDto toEntity(MaintenanceTaskDto dto)throws Exception{
        if (dto.getId() != null){
            MaintenanceTask occurrence = builder.simpleFind(MaintenanceTask.class, dto.getId());
            if (occurrence == null){
                throw new DataNotFoundException("MaintenanceTask with ID: "+ dto.getId() +" Not Found");
            }
        }
        MaintenanceTask maintenanceTask = taskMapper.toEntity(dto);
        if (builder.save(maintenanceTask) != null){
            return taskMapper.toDto(maintenanceTask);
        }
        return null;
    }
    public List<MaintenanceTaskDto> maintenanceTaskList(){
        List<MaintenanceTaskDto> dtoList = new LinkedList<>();
        List<MaintenanceTask> maintenanceTaskList = builder.findAll(MaintenanceTask.class);
        for (MaintenanceTask maintenanceTask:maintenanceTaskList){
            dtoList.add(taskMapper.toDto(maintenanceTask));
        }
        return dtoList;
    }
    public MaintenanceTaskDto findById(String maintenanceTaskId){
        return taskMapper.toDto(builder.simpleFind(MaintenanceTask.class, maintenanceTaskId));
    }
    public boolean delete(String maintenanceTaskId) throws Exception {
        return builder.deleteById(maintenanceTaskId, MaintenanceTask.class);
    }
}
