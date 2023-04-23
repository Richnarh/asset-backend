package com.khoders.entities.maintenance;

import com.khoders.entities.Employee;
import com.khoders.entities.Ref;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "start_work")
public class StartWork extends Ref {
    @JoinColumn(name = "workers", referencedColumnName = "id")
    @ManyToOne
    private Employee workers;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @JoinColumn(name = "occurrences", referencedColumnName = "id")
    @ManyToOne
    private Occurrence occurrence;

    public Employee getWorkers() {
        return workers;
    }

    public void setWorkers(Employee workers) {
        this.workers = workers;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Occurrence getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(Occurrence occurrence) {
        this.occurrence = occurrence;
    }
}
