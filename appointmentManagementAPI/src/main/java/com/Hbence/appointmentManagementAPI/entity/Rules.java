package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rules")
public class Rules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "text")
    private String text;

    @Column(name = "last_edit_at")
    private LocalDateTime lastEditAt;

    //Constructorok
    public Rules() {
    }

    public Rules(String text, LocalDateTime lastEditAt) {
        this.text = text;
        this.lastEditAt = lastEditAt;
    }

    //Getterek & Setterek
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getLastEditAt() {
        return lastEditAt;
    }

    public void setLastEditAt(LocalDateTime lastEditAt) {
        this.lastEditAt = lastEditAt;
    }
}
