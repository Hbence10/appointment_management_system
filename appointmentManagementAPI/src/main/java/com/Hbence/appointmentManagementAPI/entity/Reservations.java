package com.Hbence.appointmentManagementAPI.entity;

import java.time.LocalDateTime;

public class Reservations {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String comment;

    private ReservationType reservationType;

    private User user;

    private ReservedDates reservedDate;

    private PaymentMethods paymentMethod;

    private Status status;

    private LocalDateTime reservedAt;
    private Boolean isCanceled;
    private User canceledBy;
}
