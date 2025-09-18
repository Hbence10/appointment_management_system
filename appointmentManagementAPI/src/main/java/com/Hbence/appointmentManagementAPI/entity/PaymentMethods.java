package com.Hbence.appointmentManagementAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@NoArgsConstructor
@ToString
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
    @JsonIgnore
    @OneToMany(
            mappedBy = "paymentMethod",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    private List<Reservations> reservation;

    //Constructorok
    public PaymentMethods(String name) {
        this.name = name;
    }
}
