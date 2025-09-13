package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "devices")
public class Devices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "amount")
    @NotNull
    @Size(max = 2)
    private int amount;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "category_id")
    private DevicesCategory categoryId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {})
    @JoinTable(
            name = "devices_reservation_type",
            joinColumns = @JoinColumn(name = "reservation_type_id"),
            inverseJoinColumns = @JoinColumn(name = "device_id")
    )
    private List<ReservationType> reservationTypes;

    public Devices() {
    }

    public Devices(String name, DevicesCategory categoryId, int amount) {
        this.name = name;
        this.categoryId = categoryId;
        this.amount = amount;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Devices{" + "Id=" + Id + ", name='" + name + '\'' + ", categoryId=" + categoryId + ", amount=" + amount + '}';
    }
}
