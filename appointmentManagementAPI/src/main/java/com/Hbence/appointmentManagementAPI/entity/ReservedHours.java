package com.Hbence.appointmentManagementAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "reserved_hours")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getReservedHoursByDate", procedureName = "getReservedHoursByDate", parameters = {
                @StoredProcedureParameter(name = "dateIN", type = LocalDate.class, mode = ParameterMode.IN)
        })
})
@Getter
@Setter
@NoArgsConstructor
public class ReservedHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start")
    @NotNull
    @Size(max = 2)
    private int start;

    @Column(name = "end")
    @NotNull
    @Size(max = 2)
    private int end;

    //Kapcsolatok
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "date_id")
    private ReservedDates date;

    @JsonIgnore
    @OneToOne(mappedBy = "reservedHours", cascade = {})
    private Reservations reservationHour;

    //Constructorok
    public ReservedHours(int start, int end, ReservedDates date) {
        this.start = start;
        this.end = end;
        this.date = date;
    }

    @Override
    public String toString() {
        return "ReservedHours{" + "date=" + date + ", end=" + end + ", start=" + start + ", id=" + id + '}';
    }
}
