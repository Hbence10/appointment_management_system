package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //Foglalasok
    @GetMapping("/user/{id}")
    public List<Reservations> getReservationByUserId(@PathVariable("id") Long id) {
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

    @GetMapping("/date/{wantedDate}")
    public List<Reservations> getReservationsByDate(@PathVariable("wantedDate") String wantedDate) {
        return reservationService.getReservationByDate(wantedDate);
    }

    @PostMapping("")
    public ResponseEntity<Reservations> makeReservation(@RequestBody Reservations newReservation){
        return reservationService.makeReservation(newReservation);
    }

    @PatchMapping("/cancel/{id}")
    public String cancelReservation(@PathVariable("id") Long id, @RequestBody Map<String, Object> cancelBody){
        return reservationService.cancelReservation(id, cancelBody);
    }

    //Foglalasi tipusok
    @GetMapping("/reservationType")
    public List<ReservationType> getAllReservationTypes() {
        return reservationService.getAllReservationType();
    }

    @PostMapping("/reservationType")
    public ResponseEntity<Boolean> addNewReservationType(@RequestBody ReservationType newReservationType){
        return null;
    }

    @DeleteMapping("/reservationType/{id}")
    public ResponseEntity<Boolean> deleteReservationType(@PathVariable("id") Long id){
        return null;
    }

    @PutMapping("/reservationType")
    public ResponseEntity<Boolean> updateReservationType(@RequestBody ReservationType updatedReservationType){
        return null;
    }

    //Fizetesi modszerek
    @GetMapping("/paymentMethods")
    public ResponseEntity<List<PaymentMethods>> getAllPaymentMethod() {
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