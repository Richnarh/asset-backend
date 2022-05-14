package com.khoders.asset.services;

import com.khoders.asset.dto.maintenance.OccurrenceDto;
import com.khoders.asset.entities.maintenance.Occurrence;
import com.khoders.asset.mapper.maintenance.OccurrenceMapper;
import com.khoders.asset.utils.CrudBuilder;
import com.khoders.resource.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Transactional
@Service
public class OccurrenceService {
    @Autowired private CrudBuilder builder;
    @Autowired private OccurrenceMapper mapper;

    public OccurrenceDto toEntity(OccurrenceDto dto){
        if (dto.getId() != null){
            Occurrence occurrence = builder.simpleFind(Occurrence.class, dto.getId());
            if (occurrence == null){
                throw new DataNotFoundException("Occurrence with ID: "+ dto.getId() +" Not Found");
            }
        }
        Occurrence occurrence = mapper.toEntity(dto);
        if (builder.save(occurrence) != null){
            return mapper.toDto(occurrence);
        }
        return null;
    }
    public List<OccurrenceDto> occurrenceList(){
        List<OccurrenceDto> dtoList = new LinkedList<>();
        List<Occurrence> occurrences = builder.findAll(Occurrence.class);
        for (Occurrence occurrence:occurrences){
            dtoList.add(mapper.toDto(occurrence));
        }
        return dtoList;
    }
    public OccurrenceDto findById(String occurrenceId){
        return mapper.toDto(builder.simpleFind(Occurrence.class, occurrenceId));
    }
    public boolean delete(String occurrenceId){
        return builder.deleteById(occurrenceId, Occurrence.class);
    }
}
