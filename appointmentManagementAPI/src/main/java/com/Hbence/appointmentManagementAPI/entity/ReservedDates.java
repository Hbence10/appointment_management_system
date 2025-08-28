package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reserved_dates")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getReservedDateByMonth", procedureName = "getReservedDateByMonth", parameters = {
                @StoredProcedureParameter(name = "dateIN", type = LocalDate.class, mode = ParameterMode.IN)
        })
})
public class ReservedDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private Date date;

    @Column(name = "is_holiday")
    private Boolean isHoliday;

    @Column(name = "is_closed")
    @NotNull
    private Boolean isClosed;

    @OneToOne(mappedBy = "reservedDate", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}) //Az Instructor class-ban levo field-re mutat
    private Reservations reservation;

    @OneToMany(
            mappedBy = "date",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<ReservedHours> reservedHours;

    public ReservedDates() {
    }

    public ReservedDates(Date date, Boolean isHoliday, Boolean isClosed) {
        this.date = date;
        this.isHoliday = isHoliday;
        this.isClosed = isClosed;
    }
}
