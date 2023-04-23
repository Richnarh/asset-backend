package com.khoders.asset.mapper.maintenance;

import com.khoders.asset.dto.maintenance.InstructionSetDto;
import com.khoders.asset.dto.maintenance.InstructionStepDto;
import com.khoders.asset.exceptions.DataNotFoundException;
import com.khoders.entities.maintenance.InstructionSet;
import com.khoders.entities.maintenance.InstructionStep;
import com.khoders.springapi.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class InstructionSetMapper {
    @Autowired
    private AppService appService;

    public InstructionSet toEntity(InstructionSetDto dto) throws Exception {
        InstructionSet instructionStep = new InstructionSet();
        if (dto.getId() != null) {
            instructionStep.setId(dto.getId());
        }
        instructionStep.setInstructionName(dto.getInstructionName());
        instructionStep.setDescription(dto.getDescription());
        instructionStep.setInstructionStepList(toEntity(dto.getInstructionStepDtoList()));
        return instructionStep;
    }

    public InstructionSetDto toDto(InstructionSet set) {
        InstructionSetDto dto = new InstructionSetDto();
        if (set.getId() == null) return null;
        dto.setId(set.getId());
        dto.setInstructionName(set.getInstructionName());
        dto.setDescription(set.getDescription());
        dto.setInstructionStepDtoList(toDto(set.getInstructionStepList()));
        return dto;
    }

    public List<InstructionStep> toEntity(List<InstructionStepDto> stepDtoList) throws Exception {
        List<InstructionStep> instructionStepList = new LinkedList<>();
        for (InstructionStepDto dto : stepDtoList) {
            InstructionStep step = new InstructionStep();
            if (dto.getId() != null) {
                step.setId(dto.getId());
            }
            step.setStepName(dto.getStepName());
            if (dto.getInstructionStepId() == null) {
                throw new DataNotFoundException("Please Specify Valid Instruction Set");
            }
            InstructionSet instructionSet = appService.findById(InstructionSet.class, dto.getInstructionStepId());
            if (instructionSet != null) {
                step.setInstructionSet(instructionSet);
            }
            instructionStepList.add(step);
        }
        return instructionStepList;
    }

    public List<InstructionStepDto> toDto(List<InstructionStep> instructionStepList) {
        List<InstructionStepDto> dtoList = new LinkedList<>();
        for (InstructionStep instructionStep : instructionStepList) {
            InstructionStepDto dto = new InstructionStepDto();
            if (instructionStep.getId() == null) return null;
            dto.setStepName(dto.getStepName());
            InstructionSet instructionSet = appService.findById(InstructionSet.class, dto.getInstructionStepId());
            if (instructionStep.getInstructionSet() != null) {
                dto.setInstructionStep(instructionSet.getInstructionName());
                dto.setInstructionStepId(instructionSet.getId());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }
}
