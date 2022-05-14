package com.khoders.asset.dto.maintenance;

import com.khoders.asset.dto.BaseDto;

public class InstructionStepDto extends BaseDto {
    private String stepName;
    private String instructionStep;
    private String instructionStepId;

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
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
}
