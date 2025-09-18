package com.Hbence.appointmentManagementAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "reserved_dates")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getReservedDatesOfPeriod", procedureName = "getReservedDatesOfPeriod", parameters = {
                @StoredProcedureParameter(name = "startDateIN", type = LocalDate.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "endDateIN", type = LocalDate.class, mode = ParameterMode.IN)
        }, resultClasses = {ReservedDates.class})
})
@Getter
@Setter
@NoArgsConstructor
public class ReservedDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "is_holiday")
    @NotNull
    private Boolean isHoliday = false;

    @Column(name = "is_closed")
    @NotNull
    private Boolean isClosed = false;

    @Column(name = "is_full")
    @NotNull
    private Boolean isFull = false;

    //Kapcsolatok
    @JsonIgnore
    @OneToMany(
            mappedBy = "date",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    private List<ReservedHours> reservedHours;

    //Constructorok
    public ReservedDates(LocalDate date, Boolean isHoliday, Boolean isClosed, Boolean isFull) {
        this.date = date;
        this.isHoliday = isHoliday;
        this.isClosed = isClosed;
        this.isFull = isFull;
    }
}
