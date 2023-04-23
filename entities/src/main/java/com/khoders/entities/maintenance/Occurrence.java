package com.khoders.entities.maintenance;

import com.khoders.entities.Asset;
import com.khoders.entities.Employee;
import com.khoders.entities.Location;
import com.khoders.entities.Ref;

import javax.persistence.*;

@Entity
@Table(name = "occurrences")
public class Occurrence extends Ref {
    @JoinColumn(name = "instruction_set", referencedColumnName = "id")
    @ManyToOne
    private InstructionSet instructionSet;

    @JoinColumn(name = "asset", referencedColumnName = "id ")
    @ManyToOne
    private Asset asset;

    @JoinColumn(name = "location", referencedColumnName = "id")
    @ManyToOne
    private Location location;

    @JoinColumn(name = "equipment", referencedColumnName = "id")
    @ManyToOne
    private Asset equipment;

    @JoinColumn(name = "assigned_to", referencedColumnName = "id")
    @ManyToOne
    private Employee employee;

    @JoinColumn(name = "maintenance_task", referencedColumnName = "id")
    @ManyToOne
    private MaintenanceTask maintenanceTask;

    @Column(name = "outsourced")
    private boolean outsourced;

    public InstructionSet getInstructionSet() {
        return instructionSet;
    }

    public void setInstructionSet(InstructionSet instructionSet) {
        this.instructionSet = instructionSet;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Asset getEquipment() {
        return equipment;
    }

    public void setEquipment(Asset equipment) {
        this.equipment = equipment;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public MaintenanceTask getMaintenanceTask() {
        return maintenanceTask;
    }

    public void setMaintenanceTask(MaintenanceTask maintenanceTask) {
        this.maintenanceTask = maintenanceTask;
    }

    public boolean isOutsourced() {
        return outsourced;
    }

    public void setOutsourced(boolean outsourced) {
        this.outsourced = outsourced;
    }
}
