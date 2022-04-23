package com.khoders.asset.entities.maintenance;

import com.khoders.asset.entities.Ref;
import com.khoders.asset.entities.constants.Status;
import com.khoders.asset.entities.constants.TastPriority;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "maintenance_task")
public class MaintenanceTask extends Ref {
    @Column(name = "task_name")
    private String taskName;

    @JoinColumn(name = "request_type", referencedColumnName = "id")
    @ManyToOne
    private RequestType type;

    @Column(name = "task_priority")
    @Enumerated(EnumType.STRING)
    private TastPriority priority;

    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    private Status taskStatus;

    @Column(name = "start_date")
    private LocalDate startDate;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
