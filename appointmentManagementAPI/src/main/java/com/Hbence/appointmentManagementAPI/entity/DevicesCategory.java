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

    @OneToMany(
            mappedBy = "categoryId",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<Devices> devicesList;

    public DevicesCategory() {
    }

    public DevicesCategory(String name, List<Devices> devicesList) {
        this.name = name;
        this.devicesList = devicesList;
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

//    public List<Devices> getDevicesList() {
//        return devicesList;
//    }

    public void setDevicesList(List<Devices> devicesList) {
        this.devicesList = devicesList;
    }
}
