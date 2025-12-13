package com.Hbence.appointmentManagementAPI.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "device_category")
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

    @Column(name = "is_deleted")
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    //Kapcsolatok
    @OneToMany(
            mappedBy = "categoryId",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.REMOVE}
    )
    private List<Devices> devicesList;

    //Constructorok
    public DevicesCategory(String name, List<Devices> devicesList) {
        this.name = name;
        this.devicesList = devicesList;
    }

    public DevicesCategory(Long id) {
        this.id = id;
    }
}
