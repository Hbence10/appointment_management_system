package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "reserved_date")
public class ReservedDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start_time")
    @NotNull
    private LocalDateTime startTime;

    @Column(name = "end_time")
    @NotNull
    private LocalDateTime endTime;

    @Column(name = "is_closed")
    @NotNull
    private Boolean isClosed;

    @OneToOne(mappedBy = "reservedDate", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}) //Az Instructor class-ban levo field-re mutat
    private Reservations reservation;

    public ReservedDates() {
    }

    public ReservedDates(LocalDateTime startTime, LocalDateTime endTime, Boolean isClosed) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isClosed = isClosed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }
}
