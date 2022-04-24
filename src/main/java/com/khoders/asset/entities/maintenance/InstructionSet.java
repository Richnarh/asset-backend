package com.khoders.asset.entities.maintenance;

import com.khoders.asset.entities.Ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "instruction_set")
public class InstructionSet extends Ref {
    @Column(name = "instruction_name")
    private String instructionName;

    @Column(name = "description")
    @Lob
    private String description;

    @Column(name = "steps")
    private String steps;

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

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}
