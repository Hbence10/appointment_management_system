package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    @OneToMany(
            mappedBy = "status",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    private List<Reservations> reservationsList;

    public Status() {
    }

    public Status(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

//    public List<Reservations> getReservationsList() {
//        return reservationsList;
//    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", reservationsList=" + reservationsList +
                '}';
    }
}
