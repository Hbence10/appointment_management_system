package com.Hbence.appointmentManagementAPI.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    /*
    * A kovetkezo endpointok fognak kelleni:
    *                   - foglalások lekerdezese nap szerint
    *                   - foglalas lekerdezese userId szerint
    *                   - foglalas lemondasa
    *                   - foglalas szerkesztese
    *                   - foglalas tetele
    * */
}
