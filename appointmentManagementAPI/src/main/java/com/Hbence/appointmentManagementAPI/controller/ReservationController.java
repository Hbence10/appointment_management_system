package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.PaymentMethods;
import com.Hbence.appointmentManagementAPI.entity.ReservationType;
import com.Hbence.appointmentManagementAPI.entity.Reservations;
import com.Hbence.appointmentManagementAPI.entity.ReservedHours;
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
    public ResponseEntity<Object> makeReservation(@RequestBody Reservations newReservation){
        return reservationService.makeReservation(newReservation);
    }

    @PatchMapping("/cancel/{id}")
    public ResponseEntity<Reservations> cancelReservation(@PathVariable("id") Long id, @RequestBody Map<String, Object> cancelBody){
        return reservationService.cancelReservation(id, cancelBody);
    }

    //Foglalasi tipusok
    @GetMapping("/getReservationType")
    public ResponseEntity<List<ReservationType>> getAllReservationTypes() {
        return reservationService.getAllReservationType();
    }

    @PostMapping("/addReservationType")
    public ResponseEntity<ReservationType> addNewReservationType(@RequestBody ReservationType newReservationType){
        return reservationService.addNewReservationType(newReservationType);
    }

    @DeleteMapping("/deleteReservationType/{id}")
    public ResponseEntity<String> deleteReservationType(@PathVariable("id") Long id){
        return reservationService.deleteReservationType(id);
    }

    @PutMapping("/updateReservationType")
    public ResponseEntity<ReservationType> updateReservationType(@RequestBody ReservationType updatedReservationType){
        return reservationService.updateReservationType(updatedReservationType);
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