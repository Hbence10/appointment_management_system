package com.Hbence.appointmentManagementAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reservation_type")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReservationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "price")
    @NotNull
    @Size(max = 6)
    private int price;

    @Column(name = "is_deleted")
    @NotNull
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    private Date deletedAt;

    //Kapcsolatok
    @OneToMany(
            mappedBy = "reservationTypeId",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    @JsonIgnore
    private List<Reservations> reservation;

    @JsonIgnore
    @OneToOne(mappedBy = "reservationType", cascade = {})
    private SpecialOffer specialOffer;

    @JsonIgnore
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {},
            mappedBy = "reservationTypes"
    )
    private List<Devices> devicesList;

    //Constructorok
    public ReservationType(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
