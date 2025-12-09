package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table(name = "details")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Details {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "address")
    @NotNull
    @Size(max = 255)
    private String address;

    @Column(name = "phone")
    @NotNull
    @Size(max = 255)
    private String phone;

    @Column(name = "email")
    @NotNull
    @Size(max = 255)
    private String email;

    @Column(name = "fire_phone")
    @NotNull
    @Size(max = 255)
    private String firePhone;
}
