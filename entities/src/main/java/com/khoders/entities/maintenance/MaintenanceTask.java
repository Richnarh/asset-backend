package com.khoders.entities.maintenance;

import com.khoders.entities.Ref;
import com.khoders.entities.constants.Status;
import com.khoders.entities.constants.TaskPriority;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "maintenance_task")
public class MaintenanceTask extends Ref {
    @Column(name = "task_name")
    private String taskName;

    @JoinColumn(name = "request_type", referencedColumnName = "id")
    @ManyToOne
    private RequestType requestType;

    @Column(name = "task_priority")
    @Enumerated(EnumType.STRING)
    private TaskPriority taskPriority;

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

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public Status getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Status taskStatus) {
        this.taskStatus = taskStatus;
    }
}
