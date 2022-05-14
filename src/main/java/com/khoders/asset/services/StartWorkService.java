package com.khoders.asset.services;

import com.khoders.asset.dto.maintenance.StartWorkDto;
import com.khoders.asset.entities.maintenance.StartWork;
import com.khoders.asset.mapper.maintenance.StartWorkMapper;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.spring.ApiResponse;
import com.khoders.resource.utilities.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Transactional
@Service
public class StartWorkService {
    @Autowired
    private CrudBuilder builder;
    @Autowired private StartWorkMapper startWorkMapper;

    public StartWorkDto toEntity(StartWorkDto dto){
        if (dto.getId() != null){
            StartWork startWork = builder.simpleFind(StartWork.class, dto.getId());
            if (startWork == null){
                throw new DataNotFoundException("StartWork with ID: "+ dto.getId() +" Not Found");
            }
        }
        StartWork startWork = startWorkMapper.toEntity(dto);
        if (builder.save(startWork) != null){
            return startWorkMapper.toDto(startWork);
        }
        return null;
    }
    public List<StartWorkDto> startWorkList(){
        List<StartWorkDto> dtoList = new LinkedList<>();
        List<StartWork> startWorkList = builder.findAll(StartWork.class);
        if (startWorkList.isEmpty()){
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
        for (StartWork startWork:startWorkList){
            dtoList.add(startWorkMapper.toDto(startWork));
        }
        return dtoList;
    }
    public StartWorkDto findById(String startWorkId){
        StartWork startWork = builder.simpleFind(StartWork.class, startWorkId);
        if (startWork == null){
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
        return startWorkMapper.toDto(startWork);
    }
    public boolean delete(String startWorkId){
        return builder.deleteById(startWorkId, StartWork.class);
    }
}
