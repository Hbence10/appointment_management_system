package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table(name = "opening_details")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class OpeningDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "day_name")
    @NotNull
    @Size(max = 40)
    private String dayName;

    @Column(name = "")
    @NotNull
    @Size(max = 2)
    private Integer startHour;

    @Column(name = "")
    @NotNull
    @Size(max = 2)
    private Integer startMin;

    @Column(name = "")
    @NotNull
    @Size(max = 2)
    private Integer endHour;

    @Column(name = "")
    @NotNull
    @Size(max = 2)
    private Integer endMin;
}
