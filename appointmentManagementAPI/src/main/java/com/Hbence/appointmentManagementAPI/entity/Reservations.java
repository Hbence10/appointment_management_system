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

    @Column(name = "phone_number")
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "canceled_by")
    private User canceledBy;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_type_id")
    private ReservationType reservationTypeId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reserved_date_id")
    private ReservedDates reservedDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethods paymentMethod;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "status_id")
    private Status status;
}
