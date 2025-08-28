package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "reserved_hours")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getReservedHoursByDate", procedureName = "getReservedHoursByDate", parameters = {
                @StoredProcedureParameter(name = "dateIN", type = Date.class, mode = ParameterMode.IN)
        })
})
public class ReservedHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start")
    @NotNull
    @Size(max = 2)
    private int start;

    @Column(name = "end")
    @NotNull
    @Size(max = 2)
    private int end;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "date_id")
    private ReservedDates date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
    private Reservations reservation;

    //constructors:
    public ReservedHours() {
    }

    public ReservedHours(int start, int end, ReservedDates date) {
        this.start = start;
        this.end = end;
        this.date = date;
    }
}
