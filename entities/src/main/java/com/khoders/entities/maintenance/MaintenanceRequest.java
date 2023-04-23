package com.khoders.entities.maintenance;

import com.khoders.entities.Employee;
import com.khoders.entities.Ref;
import com.khoders.entities.constants.Status;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "maintenance_request")
public class MaintenanceRequest extends Ref {
    @JoinColumn(name = "request_type", referencedColumnName = "id")
    @ManyToOne
    private RequestType requestType;

    @Column(name = "request_title")
    private String requestTitle;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @JoinColumn(name = "assigned_user", referencedColumnName = "id")
    @ManyToOne
    private Employee assignedUser;

    @Column(name = "request_status")
    @Enumerated(EnumType.STRING)
    private Status requestStatus;

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Employee getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(Employee assignedUser) {
        this.assignedUser = assignedUser;
    }

    public Status getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Status requestStatus) {
        this.requestStatus = requestStatus;
    }
}
