package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.service.ReservationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/reservationType")
public class ReservationTypeController {

    private ReservationTypeService reservationTypeService;

    @Autowired
    public ReservationTypeController(ReservationTypeService reservationTypeService) {
        this.reservationTypeService = reservationTypeService;
    }
}
/*
 * Endpointok:
 *       - foglalasi tipusok lekerdezese
 *       - foglalasi tipus hozzaadasa
 *       - foglalasi tipus szerkesztese
 *       - foglalasi tipus torlese
 * */
