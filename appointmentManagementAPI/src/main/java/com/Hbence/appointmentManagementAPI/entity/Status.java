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
    private Long id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    //Kapcsolatok:
    @OneToMany(
            mappedBy = "status",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    private List<Reservations> reservationsList;

    //Constructorok
    public Status() {
    }

    public Status(String name) {
        this.name = name;
    }

    //Getterek & Setterek
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", reservationsList=" + reservationsList +
                '}';
    }
}
