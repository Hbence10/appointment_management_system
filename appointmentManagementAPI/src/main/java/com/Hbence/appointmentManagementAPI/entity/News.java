package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    @Size(max = 100)
    @NotNull
    private String title;


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
*/