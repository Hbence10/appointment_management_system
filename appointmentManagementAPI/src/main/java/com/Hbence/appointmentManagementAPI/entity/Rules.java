package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "rule")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Rules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "text")
    private String text;

    @Column(name = "last_edit_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastEditAt;
}
