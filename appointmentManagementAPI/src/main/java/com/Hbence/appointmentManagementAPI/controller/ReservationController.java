package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
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

    @GetMapping("")
    public List<Reservations> getReservationByUserId(@RequestParam("userId") Integer id){
        return reservationService.getReservationByUserId(id);
    }

    @GetMapping("/reservedDates")
    public List<ReservedDates> getReservationByMonth(@RequestParam("actualDate") String actualDate){
        return reservationService.getReservationByMonth(actualDate);
    }

    @GetMapping("/reservedHours")
    public List<ReservedHours> getReservedHoursByDay(@RequestParam("selectedDay") String wantedDateDay){
        return reservationService.getReservedHoursByDay(wantedDateDay);
    }

    @GetMapping("/reservationType")
    public List<ReservationType> getAllReservationTypes(){
        return reservationService.getAllReservationType();
    }

    @GetMapping("/paymentMethods")
    public List<PaymentMethods> getAllPaymentMethod(){
        return reservationService.getAllPaymentMethod();
    }
}

/*
*   PUT VS PATCH:
*       For partial updates, need to use HTTP PATCH
*       Comparison:
*               - PUT: Replaces the entire resource
*               - PATCH: Modifies only specified parts of resource (partial)
*
*       Benefits of PATCH:
*               - Efficiency: Reducing bandwidth by sending only partial changes
*               - Flexibility: Allows multiple partial updates in a single request
*
*   ------------
*   ObjectMapper
* */