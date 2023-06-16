package com.khoders.asset.services;

import com.khoders.asset.dto.maintenance.OccurrenceDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.mapper.maintenance.OccurrenceMapper;
import com.khoders.entities.maintenance.Occurrence;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.ApiResponse;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class OccurrenceService {
    @Autowired
    private AppService appService;
    @Autowired
    private OccurrenceMapper mapper;

    public OccurrenceDto toEntity(OccurrenceDto dto) throws Exception {
        if (dto.getId() != null) {
            Occurrence occurrence = appService.findById(Occurrence.class, dto.getId());
            if (occurrence == null) {
                throw new DataNotFoundException("Occurrence with ID: " + dto.getId() + " Not Found");
            }
        }
        Occurrence occurrence = mapper.toEntity(dto);
        if (appService.save(occurrence) != null) {
            return mapper.toDto(occurrence);
        }
        return null;
    }

    public List<OccurrenceDto> occurrenceList() {
        List<OccurrenceDto> dtoList = new LinkedList<>();
        List<Occurrence> occurrences = appService.findAll(Occurrence.class);
        if (occurrences.isEmpty()) {
            ApiResponse.ok(Msg.RECORD_NOT_FOUND, dtoList);
        }
        for (Occurrence occurrence : occurrences) {
            dtoList.add(mapper.toDto(occurrence));
        }
        return dtoList;
    }

    public OccurrenceDto findById(String occurrenceId) {
        return mapper.toDto(appService.findById(Occurrence.class, occurrenceId));
    }

    public boolean delete(String occurrenceId) throws Exception {
        return appService.deleteById(Occurrence.class, occurrenceId);
    }
}
