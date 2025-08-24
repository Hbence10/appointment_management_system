package com.Hbence.appointmentManagementAPI.entity;

import java.time.LocalDateTime;
import java.util.Date;

public class SpecialOffer {

    private int id;
    private String name;
    private ReservationType reservationType;
    private int offerAmount;
    private Date startDate;
    private Date endDate;
    private LocalDateTime created_at;
}
