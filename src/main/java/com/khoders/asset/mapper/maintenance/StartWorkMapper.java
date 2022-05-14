package com.khoders.asset.mapper.maintenance;

import com.khoders.asset.dto.maintenance.StartWorkDto;
import com.khoders.asset.entities.Employee;
import com.khoders.asset.entities.maintenance.Occurrence;
import com.khoders.asset.entities.maintenance.StartWork;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.exception.DataNotFoundException;
import com.khoders.resource.utilities.DateUtil;
import com.khoders.resource.utilities.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class StartWorkMapper {
    @Autowired private CrudBuilder builder;

    public StartWork toEntity(StartWorkDto dto){
        StartWork work = new StartWork();
        if (dto.getId() != null){
            work.setId(dto.getId());
        }
        work.setStartDate(DateUtil.parseLocalDate(dto.getStartDate(), Pattern._yyyyMMdd));
        work.setRefNo(work.getRefNo());
        if (dto.getWorkersId() == null){
            throw new DataNotFoundException("Please Specify Valid WorkerId");
        }
        if (dto.getOccurrenceId() == null){
            throw new DataNotFoundException("Please Specify Valid Occurrence");
        }
        Employee worker = builder.simpleFind(Employee.class, dto.getWorkersId());
        if (worker != null){
            work.setWorkers(worker);
        }
        Occurrence occurrence = builder.simpleFind(Occurrence.class, dto.getOccurrenceId());
        if (occurrence != null){
            work.setOccurrence(occurrence);
        }
        work.setStartTime(LocalTime.parse(dto.getStartTime()));
        return work;
    }
    public StartWorkDto toDto(StartWork work){
        StartWorkDto dto = new StartWorkDto();
        if (work.getId() == null) return null;
        dto.setId(work.getId());
        dto.setStartDate(DateUtil.parseLocalDateString(work.getStartDate(), Pattern.ddMMyyyy));
        dto.setStartTime(work.getStartTime().toString());
        if (work.getWorkers() != null){
            dto.setWorkersId(work.getWorkers().getId());
            dto.setWorkersName(work.getWorkers().getEmailAddress());
        }
        if (work.getOccurrence() != null){
            dto.setOccurrenceId(work.getOccurrence().getId());
            dto.setOccurrence(work.getOccurrence().getRefNo());
        }
        return dto;
    }
}
