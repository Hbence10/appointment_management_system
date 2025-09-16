package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "rules")
@Getter
@Setter
@NoArgsConstructor
public class Rules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "text")
    private String text;

    @Column(name = "last_edit_at")
    private LocalDateTime lastEditAt;

    //Constructorok
    public Rules(String text, LocalDateTime lastEditAt) {
        this.text = text;
        this.lastEditAt = lastEditAt;
    }
}
