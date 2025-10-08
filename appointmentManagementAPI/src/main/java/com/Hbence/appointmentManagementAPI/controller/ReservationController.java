package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    //Foglalasok
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Reservations>> getReservationByUserId(@PathVariable("id") Long id) {
        return reservationService.getReservationByUserId(id);
    }

    @GetMapping("/reservedDates")
    public ResponseEntity<Object> getReservationByMonth(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return reservationService.getReservationByMonth(startDate, endDate);
    }

    @GetMapping("/reservedHours")
    public ResponseEntity<List<ReservedHours>> getReservedHoursByDay(@RequestParam("selectedDay") String wantedDateDay) {
        return reservationService.getReservedHoursByDay(wantedDateDay);
    }

    @GetMapping("/date/{wantedDate}")
    public ResponseEntity<List<Reservations>> getReservationsByDate(@PathVariable("wantedDate") String wantedDate) {
        return reservationService.getReservationByDate(wantedDate);
    }

    @PostMapping("/makeReservation")
    public ResponseEntity<Object> makeReservation(@RequestBody Reservations newReservation) {
        return reservationService.makeReservation(newReservation);
    }

    @PatchMapping("/cancel/{id}")
    public ResponseEntity<Reservations> cancelReservation(@PathVariable("id") Long id, @RequestBody Users canceledBy) {
        return reservationService.cancelReservation(id, canceledBy);
    }

    //-------------
    @PostMapping("/getByEmailAndVCode")
    public ResponseEntity<Object> getReservationByEmailAndVCode(@RequestBody Map<String, String> requestBody){
        return reservationService.getReservationByEmailAndVCode(requestBody.get("email"), requestBody.get("vCode"));
    }
}