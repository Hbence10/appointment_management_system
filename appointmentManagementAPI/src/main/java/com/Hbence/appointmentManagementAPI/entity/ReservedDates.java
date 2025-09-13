package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reserved_dates")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getReservedDatesOfPeriod", procedureName = "getReservedDatesOfPeriod", parameters = {
                @StoredProcedureParameter(name = "startDateIN", type = LocalDate.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "endDateIN", type = LocalDate.class, mode = ParameterMode.IN)
        }, resultClasses = {ReservedDates.class})
})
public class ReservedDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

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

    @OneToMany(
            mappedBy = "date",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    private List<ReservedHours> reservedHours;

    public ReservedDates() {
    }

    public ReservedDates(LocalDate date, Boolean isHoliday, Boolean isClosed, Boolean isFull) {
        this.date = date;
        this.isHoliday = isHoliday;
        this.isClosed = isClosed;
        this.isFull = isFull;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getId() {return id;}

    public Boolean getIsHoliday() {
        return isHoliday;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public Boolean getIsFull() {
        return isFull;
    }

    public List<ReservedHours> getReservedHours() {
        return reservedHours;
    }
}
