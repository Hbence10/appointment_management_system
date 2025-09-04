package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "reservation_type")
public class ReservationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "price")
    @NotNull
    @Size(max = 6)
    private int price;

    @OneToMany(
            mappedBy = "reservationTypeId",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<Reservations> reservation;

    @OneToOne(mappedBy = "reservationType", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}) //Az Instructor class-ban levo field-re mutat
    private SpecialOffer specialOffer;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {},
            mappedBy = "reservationTypes"
    )
    private List<Devices> devicesList;

    public ReservationType() {
    }

    public ReservationType(String name, int price) {
        this.name = name;
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ReservationType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", reservation=" + reservation +
                ", specialOffer=" + specialOffer +
                ", devicesList=" + devicesList +
                '}';
    }
}
