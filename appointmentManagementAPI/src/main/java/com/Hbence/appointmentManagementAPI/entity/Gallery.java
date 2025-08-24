package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "gallery")
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

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

    public Gallery() {
    }

    public Gallery(String photoName, String photoPath, int placement) {
        this.photoName = photoName;
        this.photoPath = photoPath;
        this.placement = placement;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getPlacement() {
        return placement;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
    }

    @Override
    public String toString() {
        return "Gallery{" + "Id=" + Id + ", photoName='" + photoName + '\'' + ", photoPath='" + photoPath + '\'' + ", placement=" + placement + '}';
    }
}
