package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.service.ReservationService;
import com.Hbence.appointmentManagementAPI.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/user/{id}")
    public List<Reservations> getReservationByUserId(@PathVariable("id") Integer id) {
        System.out.println(id.getClass());
        return reservationService.getReservationByUserId(id);
    }

    @GetMapping("/reservedDates")
    public List<ReservedDates> getReservationByMonth(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return reservationService.getReservationByMonth(startDate, endDate);
    }

    @GetMapping("/reservedHours")
    public List<ReservedHours> getReservedHoursByDay(@RequestParam("selectedDay") String wantedDateDay) {
        return reservationService.getReservedHoursByDay(wantedDateDay);
    }

    @GetMapping("/reservationType")
    public List<ReservationType> getAllReservationTypes() {
        return reservationService.getAllReservationType();
    }

    @GetMapping("/paymentMethods")
    public List<PaymentMethods> getAllPaymentMethod() {
        return reservationService.getAllPaymentMethod();
    }

    @GetMapping("/date/{wantedDate}")
    public List<Reservations> getReservationsByDate(@PathVariable("wantedDate") String wantedDate) {
        return reservationService.getReservationByDate(wantedDate);
    }

    @PostMapping("")
    public Response makeReservation(@RequestBody Reservations newReservation){
        System.out.println(newReservation);

        return reservationService.makeReservation(newReservation);
//        return null;
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