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


    private Reservations reservation;
}
