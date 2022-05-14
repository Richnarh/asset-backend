package com.khoders.asset.entities.maintenance;
import com.khoders.asset.entities.Ref;
import javax.persistence.*;

@Entity
@Table(name = "instruction_steps")
public class InstructionStep extends Ref {
    @Column(name = "step_name")
    @Lob
    private String stepName;

    public static final String _instructionSet="instructionSet";
    @JoinColumn(name = "instruction_set", referencedColumnName = "id")
    @ManyToOne
    private InstructionSet instructionSet;

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public InstructionSet getInstructionSet() {
        return instructionSet;
    }

    public void setInstructionSet(InstructionSet instructionSet) {
        this.instructionSet = instructionSet;
    }
}
