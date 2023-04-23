package com.khoders.entities.maintenance;

import com.khoders.entities.Ref;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "instruction_set")
public class InstructionSet extends Ref {
    @Column(name = "instruction_name")
    private String instructionName;

    @Column(name = "description")
    @Lob
    private String description;

    @Transient
    private List<InstructionStep> instructionStepList = new LinkedList<>();

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

    public List<InstructionStep> getInstructionStepList() {
        return instructionStepList;
    }

    public void setInstructionStepList(List<InstructionStep> instructionStepList) {
        this.instructionStepList = instructionStepList;
    }
}
