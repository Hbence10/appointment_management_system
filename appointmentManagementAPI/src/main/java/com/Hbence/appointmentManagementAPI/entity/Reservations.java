package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "reservations")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getReservationByUserId", procedureName = "getReservationByUserId", parameters = {
                @StoredProcedureParameter(name = "userIdIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = {Reservations.class}),

        @NamedStoredProcedureQuery(name = "getReservationByDate", procedureName = "getReservationByDate", parameters = {
                @StoredProcedureParameter(name = "dateIN", type = LocalDate.class, mode = ParameterMode.IN)
        }, resultClasses = {Long.class})
})
@Setter
@Getter
@NoArgsConstructor
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    private Date reservedAt;

    @Column(name = "is_canceled", columnDefinition = "boolean default false")
    private Boolean isCanceled = false;

    @Column(name = "canceled_at")
    private LocalDate canceledAt;

    //Kapcsolatok
    @OneToOne(cascade = {})
    @JoinColumn(name = "canceled_by")
    @Null
    private User canceledBy;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "user_id")
    @Null
    private User user;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "reservation_type_id")
    private ReservationType reservationTypeId;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "payment_method_id")
    private PaymentMethods paymentMethod;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "reserved_hour_id")
    private ReservedHours reservedHours;

    //Constructorok:
    public Reservations(String firstName, String lastName, String email, String phone, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Reservations{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", comment='" + comment + '\'' +
                ", reservedAt=" + reservedAt +
                ", isCanceled=" + isCanceled +
                ", canceledAt=" + canceledAt +
                ", canceledBy=" + canceledBy +
                ", user=" + user +
                ", reservationTypeId=" + reservationTypeId +
                ", paymentMethod=" + paymentMethod +
                ", status=" + status +
                ", reservedHours=" + reservedHours +
                '}';
    }
}
