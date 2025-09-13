package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "devices_category")
public class DevicesCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    //Kapcsolatok
    @OneToMany(
            mappedBy = "categoryId",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    private List<Devices> devicesList;

    //Constructorok
    public DevicesCategory() {
    }

    public DevicesCategory(String name, List<Devices> devicesList) {
        this.name = name;
        this.devicesList = devicesList;
    }

    //Getterek & Setterek
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Devices> getDevicesList() {
        return devicesList;
    }
}
