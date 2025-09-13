package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "payment_methods")
public class PaymentMethods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    //Kapcsolatok
    @OneToMany(
            mappedBy = "paymentMethod",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    private List<Reservations> reservation;

    //Constructorok
    public PaymentMethods() {
    }

    public PaymentMethods(String name) {
        this.name = name;
    }

    //Getterek & Setterek
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
