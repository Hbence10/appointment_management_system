package com.Hbence.appointmentManagementAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

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

    @Column(name = "is_deleted")
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    //Constructorok
    public Gallery(String photoName, String photoPath, int placement) {
        this.photoName = photoName;
        this.photoPath = photoPath;
        this.placement = placement;
    }
}
