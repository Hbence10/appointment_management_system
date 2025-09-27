package com.Hbence.appointmentManagementAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @Size(max = 100)
    @NotNull
    private String title;

    @Column(name = "text")
    @NotNull
    private String text;

    @Column(name = "banner_img_path")
    @Null
    private String bannerImgPath;

    @Column(name = "placement")
    @NotNull
    @Size(max = 2)
    private Integer placement;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "deleted_at")
    @Null
    private Date deletedAt;

    @Column(name = "last_edit_at")
    @Null
    private Date lastEditAt;

    //Kapcsolatok:
    @ManyToOne(cascade = {})
    @JoinColumn(name = "writer_id")
    private Users writer;

    //Constructorok:
    public News(String title, String text, String bannerImgPath, Users writer, Integer placement) {
        this.title = title;
        this.text = text;
        this.bannerImgPath = bannerImgPath;
        this.writer = writer;
        this.placement = placement;
    }
}

/*
   https://www.youtube.com/watch?v=i4CNH8pi3X4

   Validacio:
           - a Validation a Java applikacion belul folyik
           - a Hibernate nem hajt vegre validaciot, csak not-null constraint add hozza

    Mikor kerul checkelesre?
            @NotNull:
                - During per-persist and pre-update

            @Column(nullable=false):
                - No validation in persistence layer
                - SQL statement might fail

    @Column(nullable=false)-t csak akkor erdemes hasznalni, ha a hibernate generalja ki a table-t

    GenerationType.IDENTITY --> a primary key-kent hivatkozik ra
*/