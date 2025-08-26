package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.PaymentMethods;
import com.Hbence.appointmentManagementAPI.entity.Reservations;
import com.Hbence.appointmentManagementAPI.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/paymentMethods")
    public List<PaymentMethods> getAllPaymentMethod(){
        return reservationService.getAllPaymentMethod();
    }

    @GetMapping("")
    public ArrayList<Reservations> getReservationByUserId(@RequestParam("userId") Integer id){
        return reservationService.getReservationByUserId(id);
    }
}
/*
 * A kovetkezo endpointok fognak kelleni:
 *                   - foglalások lekerdezese nap szerint
 *                   - foglalas lekerdezese userId szerint
 *                   - foglalas lemondasa
 *                   - foglalas szerkesztese
 *                   - foglalas tetele
 *                   - fizetesi mod lekerdezese
 * */