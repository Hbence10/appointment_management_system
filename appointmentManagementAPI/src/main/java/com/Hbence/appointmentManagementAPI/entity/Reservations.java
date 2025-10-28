package com.Hbence.appointmentManagementAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "reservation")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getReservationByUserId", procedureName = "getReservationByUserId", parameters = {
                @StoredProcedureParameter(name = "userIdIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = {Reservations.class}),

        @NamedStoredProcedureQuery(name = "getReservationByDate", procedureName = "getReservationByDate", parameters = {
                @StoredProcedureParameter(name = "dateIN", type = LocalDate.class, mode = ParameterMode.IN)
        }, resultClasses = {Long.class}),

        @NamedStoredProcedureQuery(name = "getReservationsByEmail", procedureName = "getReservationsByEmail", parameters = {
                @StoredProcedureParameter(name = "emailIN", type = String.class, mode = ParameterMode.IN)
        }, resultClasses = {Reservations.class}),

        @NamedStoredProcedureQuery(name = "getAllReservationEmail", procedureName = "getAllReservationEmail", resultClasses = {String.class}),

        @NamedStoredProcedureQuery(name = "getReservationsForAdminReservation", procedureName = "getReservationsForAdminReservation", parameters = {
                @StoredProcedureParameter(name = "startDateIN", type = LocalDate.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "endDateIN", type = LocalDate.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "startHourIN", type = Integer.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "endHourIN", type = Integer.class, mode = ParameterMode.IN)
        })
})
@Setter
@Getter
@NoArgsConstructor
@ToString
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

    @JsonIgnore
    @Column(name = "cancel_v_code")
    @Null
    private String cancelVCode;

    @Column(name = "canceler_email")
    @Null
    @Size(max = 100)
    private String cancelerEmail;

    //Kapcsolatok
    @OneToOne(cascade = {})
    @JoinColumn(name = "canceled_by")
    @Null
    private Users canceledBy;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "user_id")
    @Null
    private Users user;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "reservation_type_id")
    @Null
    private ReservationType reservationTypeId;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "payment_method_id")
    @Null
    private PaymentMethods paymentMethod;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "reserved_hour_id")
    private ReservedHours reservedHours;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "phone_country_code_id")
    private PhoneCountryCode phoneCountryCode;

    //Constructorok:
    public Reservations(String firstName, String lastName, String email, String phone, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.comment = comment;
    }
}
