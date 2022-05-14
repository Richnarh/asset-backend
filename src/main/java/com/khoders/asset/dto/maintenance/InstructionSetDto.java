package com.khoders.asset.dto.maintenance;

import com.khoders.asset.dto.BaseDto;

import java.util.LinkedList;
import java.util.List;

public class InstructionSetDto extends BaseDto {
    private String instructionName;
    private String description;
    private String instructionStep;
    private String instructionStepId;
    private List<InstructionStepDto> instructionStepDtoList = new LinkedList<>();

    public String getInstructionName() {
        return instructionName;
    }

    public void setInstructionName(String instructionName) {
        this.instructionName = instructionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructionStep() {
        return instructionStep;
    }

    public void setInstructionStep(String instructionStep) {
        this.instructionStep = instructionStep;
    }

    public String getInstructionStepId() {
        return instructionStepId;
    }

    public void setInstructionStepId(String instructionStepId) {
        this.instructionStepId = instructionStepId;
    }

    public List<InstructionStepDto> getInstructionStepDtoList() {
        return instructionStepDtoList;
    }

    public void setInstructionStepDtoList(List<InstructionStepDto> instructionStepDtoList) {
        this.instructionStepDtoList = instructionStepDtoList;
    }
}
