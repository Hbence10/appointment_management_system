package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "gallery")
@Getter
@Setter
@NoArgsConstructor
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

    @Override
    public String toString() {
        return "Gallery{" + "Id=" + Id + ", photoName='" + photoName + '\'' + ", photoPath='" + photoPath + '\'' + ", placement=" + placement + '}';
    }
}
