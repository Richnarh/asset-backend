package com.khoders.asset.services;

import com.khoders.asset.dto.Sql;
import com.khoders.asset.dto.maintenance.InstructionSetDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.asset.mapper.maintenance.InstructionSetMapper;
import com.khoders.entities.maintenance.InstructionSet;
import com.khoders.entities.maintenance.InstructionStep;
import com.khoders.resource.utilities.Msg;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class InstructionSetService {
    private AppService appService;
    private InstructionSetMapper mapper;
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public InstructionSetDto save(InstructionSetDto dto) throws Exception {
        if (dto.getId() != null) {
            InstructionSet instruction = appService.findById(InstructionSet.class, dto.getId());
            if (instruction == null) {
                throw new DataNotFoundException("InstructionSet with ID: " + dto.getId() + " Not Found");
            }
        }
        InstructionSet instructionSet = mapper.toEntity(dto);
        if (appService.save(instructionSet) != null) {
            for (InstructionStep instructionStep : instructionSet.getInstructionStepList()) {
                instructionStep.setInstructionSet(instructionSet);
                appService.save(instructionStep);
            }
        }
        return mapper.toDto(instructionSet);
    }

    public List<InstructionSetDto> instructionSetList() throws Exception {

        List<InstructionStep> instructionStepList;
        List<InstructionSetDto> instructionList = new LinkedList<>();
        List<InstructionSet> instructionSetList = appService.findAll(InstructionSet.class);
        if (instructionSetList.isEmpty()) {
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
        for (InstructionSet instructionSet : instructionSetList) {
            SqlParameterSource param = new MapSqlParameterSource(InstructionStep._instructionSetId, instructionSet.getId());
            instructionStepList = jdbc.query(Sql.INSTRUCTION_SET_BY_ID, param, BeanPropertyRowMapper.newInstance(InstructionStep.class));
            instructionSet.setInstructionStepList(instructionStepList);
            instructionSetList = new LinkedList<>();
            instructionSetList.add(instructionSet);
        }
        for (InstructionSet instructionSet : instructionSetList) {
            instructionList.add(mapper.toDto(instructionSet));
        }
        return instructionList;
    }

    public InstructionSetDto findById(String instructionSetId) throws Exception {
        List<InstructionStep> instructionStepList;

        InstructionSet instructionSet = appService.findById(InstructionSet.class, instructionSetId);
        if (instructionSet == null) {
            throw new DataNotFoundException(Msg.RECORD_NOT_FOUND);
        }
        SqlParameterSource param = new MapSqlParameterSource(InstructionStep._instructionSetId, instructionSet.getId());
        instructionStepList = jdbc.query(Sql.INSTRUCTION_SET_BY_ID, param, BeanPropertyRowMapper.newInstance(InstructionStep.class));
        instructionSet.setInstructionStepList(instructionStepList);
        return mapper.toDto(instructionSet);
    }

    public boolean delete(String instructionSetId) throws Exception {
        return appService.deleteById(InstructionSet.class, instructionSetId);
    }
}
