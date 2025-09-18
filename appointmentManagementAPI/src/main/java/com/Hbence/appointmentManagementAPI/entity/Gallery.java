package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "gallery")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "photo_name")
    @NotNull
    private String photoName;

    @Column(name = "photo_path")
    @NotNull
    private String photoPath;

    @Column(name = "placement")
    @NotNull
    @Size(max = 2)
    private int placement;

    //Constructorok
    public Gallery(String photoName, String photoPath, int placement) {
        this.photoName = photoName;
        this.photoPath = photoPath;
        this.placement = placement;
    }
}
