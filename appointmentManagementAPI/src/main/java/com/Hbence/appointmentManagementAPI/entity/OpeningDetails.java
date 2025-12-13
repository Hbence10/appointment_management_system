package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Table(name = "opening_detail")
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

    @Column(name = "start_time")
    @NotNull
    @Temporal(TemporalType.TIME)
    private Date startTime;

    @Column(name = "end_time")
    @NotNull
    @Temporal(TemporalType.TIME)
    private Date endTime;
}
