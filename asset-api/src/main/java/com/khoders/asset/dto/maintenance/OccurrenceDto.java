package com.khoders.asset.dto.maintenance;

import com.khoders.asset.dto.BaseDto;

public class OccurrenceDto extends BaseDto {
    private String instructionSet;
    private String instructionSetId;
    private String assetName;
    private String assetId;
    private String locationName;
    private String locationId;
    private String equipment;
    private String equipmentId;
    private String employeeName;
    private String employeeId;
    private String maintenanceTask;
    private String maintenanceTaskId;
    private boolean outsourced;

    public String getInstructionSet() {
        return instructionSet;
    }

    public void setInstructionSet(String instructionSet) {
        this.instructionSet = instructionSet;
    }

    public String getInstructionSetId() {
        return instructionSetId;
    }

    public void setInstructionSetId(String instructionSetId) {
        this.instructionSetId = instructionSetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getMaintenanceTask() {
        return maintenanceTask;
    }

    public void setMaintenanceTask(String maintenanceTask) {
        this.maintenanceTask = maintenanceTask;
    }

    public String getMaintenanceTaskId() {
        return maintenanceTaskId;
    }

    public void setMaintenanceTaskId(String maintenanceTaskId) {
        this.maintenanceTaskId = maintenanceTaskId;
    }

    public boolean isOutsourced() {
        return outsourced;
    }

    public void setOutsourced(boolean outsourced) {
        this.outsourced = outsourced;
    }
}
