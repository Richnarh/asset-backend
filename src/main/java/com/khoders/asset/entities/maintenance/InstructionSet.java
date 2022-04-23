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
}
