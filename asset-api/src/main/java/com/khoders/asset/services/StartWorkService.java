package com.khoders.asset.services;

import com.khoders.asset.dto.maintenance.StartWorkDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.mapper.maintenance.StartWorkMapper;
import com.khoders.entities.maintenance.StartWork;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class StartWorkService {
    @Autowired
    private AppService appService;
    @Autowired
    private StartWorkMapper startWorkMapper;

    public StartWorkDto toEntity(StartWorkDto dto) throws Exception {
        if (dto.getId() != null) {
            StartWork startWork = appService.findById(StartWork.class, dto.getId());
            if (startWork == null) {
                throw new DataNotFoundException("StartWork with ID: " + dto.getId() + " Not Found");
            }
        }
        StartWork startWork = startWorkMapper.toEntity(dto);
        if (appService.save(startWork) != null) {
            return startWorkMapper.toDto(startWork);
        }
        return null;
    }

    public List<StartWorkDto> startWorkList() throws Exception {
        List<StartWorkDto> dtoList = new LinkedList<>();
        List<StartWork> startWorkList = appService.findAll(StartWork.class);
        if (startWorkList.isEmpty()) {
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
        for (StartWork startWork : startWorkList) {
            dtoList.add(startWorkMapper.toDto(startWork));
        }
        return dtoList;
    }

    public StartWorkDto findById(String startWorkId) throws Exception {
        StartWork startWork = appService.findById(StartWork.class, startWorkId);
        if (startWork == null) {
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
        return startWorkMapper.toDto(startWork);
    }

    public boolean delete(String startWorkId) throws Exception {
        return appService.deleteById(StartWork.class, startWorkId);
    }
}
