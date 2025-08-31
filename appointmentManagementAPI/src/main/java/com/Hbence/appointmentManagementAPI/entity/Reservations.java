package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getReservationByUserId", procedureName = "getReservationByUserId", parameters = {
                @StoredProcedureParameter(name = "userIdIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = {Reservations.class}),

        @NamedStoredProcedureQuery(name = "getReservationByDate", procedureName = "getReservationByDate", parameters = {
                @StoredProcedureParameter(name = "dateIN", type = LocalDate.class, mode = ParameterMode.IN)
        }, resultClasses = {Reservations.class})
})
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

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "canceled_by")
    private User canceledBy;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "reservation_type_id")
    private ReservationType reservationTypeId;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "payment_method_id")
    private PaymentMethods paymentMethod;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToOne(mappedBy = "reservationHour", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private ReservedHours reservedHours;

    public Reservations() {
    }

    public Reservations(String firstName, String lastName, String email, String phone, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.comment = comment;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }

    public Boolean getCanceled() {
        return isCanceled;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public String getReservationTypeId() {
        return reservationTypeId.getName();
    }

    public String getPaymentMethod() {
        return paymentMethod.getName();
    }

    public String getStatus() {
        return status.getName();
    }

    public String getReservedHours() {
        return reservedHours.getStart() + ":00 - " + reservedHours.getEnd() + ":00";
    }
}
