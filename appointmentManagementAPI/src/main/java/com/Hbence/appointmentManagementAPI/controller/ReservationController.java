package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
}
/*
 * A kovetkezo endpointok fognak kelleni:
 *                   - foglalások lekerdezese nap szerint
 *                   - foglalas lekerdezese userId szerint
 *                   - foglalas lemondasa
 *                   - foglalas szerkesztese
 *                   - foglalas tetele
 * */