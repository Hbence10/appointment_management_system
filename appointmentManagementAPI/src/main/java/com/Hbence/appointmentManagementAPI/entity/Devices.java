package com.Hbence.appointmentManagementAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
public class Devices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "amount")
    @NotNull
    @Size(max = 2)
    private int amount;

    //Kapcsolatok:
    @ManyToOne(cascade = {})
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private DevicesCategory categoryId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {})
    @JoinTable(
            name = "devices_reservation_type",
            joinColumns = @JoinColumn(name = "reservation_type_id"),
            inverseJoinColumns = @JoinColumn(name = "device_id")
    )
    @JsonIgnore
    private List<ReservationType> reservationTypes;

    //Constructorok:
    public Devices(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Devices{" + "Id=" + Id + ", name='" + name + '\'' + ", categoryId=" + categoryId + ", amount=" + amount + '}';
    }
}
