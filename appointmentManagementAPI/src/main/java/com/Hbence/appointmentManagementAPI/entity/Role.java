package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    //Kapcsolatok
    @OneToMany(
            mappedBy = "role",
            fetch = FetchType.LAZY,
            cascade = {}
    )
    private List<User> users;

    //Constructorok
    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    //Getterek & Setterek
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
