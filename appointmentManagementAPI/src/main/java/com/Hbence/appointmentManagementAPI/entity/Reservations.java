package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    @NotNull
    @Size(max = 100)
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    @Size(max = 100)
    private String lastName;

    @Column(name = "email")
    @NotNull
    @Size(max = 100)
    private String email;

    @Column(name = "phone")
    @NotNull
    @Size(max = 100)
    private String phone;

    @Column(name = "comment")
    @Null
    private String comment;

    @Column(name = "reserved_at")
    private LocalDateTime reservedAt;

    @Column(name = "is_canceled")
    private Boolean isCanceled;

    private User canceledBy;
    private ReservationType reservationType;
    private User user;
    private ReservedDates reservedDate;
    private PaymentMethods paymentMethod;
    private Status status;
}
