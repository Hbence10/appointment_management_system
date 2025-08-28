package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.PaymentMethods;
import com.Hbence.appointmentManagementAPI.entity.Reservations;
import com.Hbence.appointmentManagementAPI.service.ReservationService;
import com.Hbence.appointmentManagementAPI.service.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private ReservationService reservationService;
    private ObjectMapper objectMapper;

    @Autowired
    public ReservationController(ReservationService reservationService, ObjectMapper objectMapper) {
        this.reservationService = reservationService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/paymentMethods")
    public List<PaymentMethods> getAllPaymentMethod(){
        return reservationService.getAllPaymentMethod();
    }

    @GetMapping("")
    public ArrayList<Reservations> getReservationByUserId(@RequestParam("userId") Integer id){
        return reservationService.getReservationByUserId(id);
    }

    @PatchMapping("/cancel/{id}")
    public Response cancelReservation(@PathVariable int id, @RequestParam Map<String, Object> reservationBody){

        return null;
    }

    @GetMapping("/reservedDatesByDay")
    public Response getReservedDatesByDay(@RequestParam("wantedDate") String wantedDate){
        return reservationService.getReservedDatesByDate(wantedDate);
    }

    public Response getReservedDatesByMonth(){


        return null;
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