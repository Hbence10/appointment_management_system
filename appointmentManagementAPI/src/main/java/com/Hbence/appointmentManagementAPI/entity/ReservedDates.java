package com.Hbence.appointmentManagementAPI.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reserved_date")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getReservedDatesOfPeriod", procedureName = "getReservedDatesOfPeriod", parameters = {
                @StoredProcedureParameter(name = "startDateIN", type = LocalDate.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "endDateIN", type = LocalDate.class, mode = ParameterMode.IN)
        }, resultClasses = {ReservedDates.class}),

        @NamedStoredProcedureQuery(name = "getReservedDateByDate", procedureName = "getReservedDateByDate", parameters = {
                @StoredProcedureParameter(name = "dateIN", type = LocalDate.class, mode = ParameterMode.IN)
        }, resultClasses = {ReservedDates.class}),

        @NamedStoredProcedureQuery(name = "getReservedDateBetweenTwoDateByDate", procedureName = "getReservedDateBetweenTwoDateByDate", parameters = {
                @StoredProcedureParameter(name = "startDateIN", type = LocalDate.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "endDateIN", type = LocalDate.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "dateIN", type = LocalDate.class, mode = ParameterMode.IN)
        }, resultClasses = {ReservedDates.class})
})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReservedDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "is_deleted")
    @NotNull
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    @JsonIgnore
    private LocalDateTime deletedAt;

    //Kapcsolatok
    @JsonIgnoreProperties({"date", "reservationHour"})
    @OneToMany(
            mappedBy = "date",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.REMOVE}
    )
    private List<ReservedHours> reservedHours;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "close_reason_id")
    @Null
    private CloseReason closeReason;

    //Constructorok
    public ReservedDates(LocalDate date) {
        this.date = date;
    }

    public ReservedDates(LocalDate date, CloseReason closeReason) {
        this.date = date;
        this.closeReason = closeReason;
    }
}
