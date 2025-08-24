package com.Hbence.appointmentManagementAPI.entity;

import java.time.LocalDateTime;

public class ReservedDates {

    private int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isClosed;

    private Reservations reservation;
}
