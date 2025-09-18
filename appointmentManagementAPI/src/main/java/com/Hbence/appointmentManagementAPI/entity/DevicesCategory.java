package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "devices_category")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DevicesCategory {

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
            mappedBy = "categoryId",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    private List<Devices> devicesList;

    //Constructorok
    public DevicesCategory(String name, List<Devices> devicesList) {
        this.name = name;
        this.devicesList = devicesList;
    }
}
