package com.khoders.asset.dto.maintenance;

import com.khoders.asset.dto.BaseDto;

public class StartWorkDto extends BaseDto {
    private String workersName;
    private String workersId;
    private String startDate;
    private String startTime;
    private String occurrence;
    private String occurrenceId;

    public String getWorkersName() {
        return workersName;
    }

    public void setWorkersName(String workersName) {
        this.workersName = workersName;
    }

    public String getWorkersId() {
        return workersId;
    }

    public void setWorkersId(String workersId) {
        this.workersId = workersId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(String occurrence) {
        this.occurrence = occurrence;
    }

    public String getOccurrenceId() {
        return occurrenceId;
    }

    public void setOccurrenceId(String occurrenceId) {
        this.occurrenceId = occurrenceId;
    }
}
