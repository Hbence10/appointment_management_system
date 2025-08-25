package com.Hbence.appointmentManagementAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "special_offer")
public class SpecialOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "offer_amount")
    @NotNull
    @Size(max = 2)
    private int offerAmount;

    @Column(name = "start_date")
    @NotNull
    private Date startDate;

    @Column(name = "end_date")
    @NotNull
    private Date endDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private ReservationType reservationType;
}
