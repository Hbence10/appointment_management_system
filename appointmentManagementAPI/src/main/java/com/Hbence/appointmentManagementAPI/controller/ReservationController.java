package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
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
    public ResponseEntity<Object> getReservationBetweenIntervallum(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return reservationService.getReservationBetweenIntervallum(startDate, endDate);
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

    //Foglalas lemondasa
    @PatchMapping("/cancel/{id}")
    public ResponseEntity<Reservations> cancelReservation(@PathVariable("id") Long id, @RequestBody Users canceledBy) {
        return reservationService.cancelReservation(id, canceledBy);
    }

    @PostMapping("/getByEmailAndVCode")
    public ResponseEntity<Object> getReservationByEmailAndVCode(@RequestBody Map<String, String> requestBody){
        return reservationService.getReservationByEmailAndVCode(requestBody.get("email"), requestBody.get("vCode"));
    }

    //ADMIN PAGE
    //Tovabbi foglalasi tipus
    @PostMapping("/adminReservation")
    public ResponseEntity<Object> makeAdminReservation(@RequestBody Map<String, Object> bodyObject){
        return reservationService.makeAdminReservation((ReservedHours) bodyObject.get("selectedHour"), (Long) bodyObject.get("adminId"));
    }

    @PostMapping("/makeReservationAlwaysBetweenTwoDates")
    public ResponseEntity<Object> makeReservationBetweenPeriod(@RequestBody Map<String, Object> body){
        return reservationService.makeReservationBetweenPeriod(body.get("startDate").toString(), body.get("endDate").toString(), (ReservedHours) body.get("selectedHour"), (Long) body.get("adminId"));
    }

    @PostMapping("/makeReservationByRepetitiveDates")
    public ResponseEntity<Object> makeReservationByRepetitiveDates(@RequestBody Map<String, Object> body){
        return reservationService.makeReservationByRepetitiveDates(body.get("startDate").toString(), body.get("endDate").toString(), (ArrayList<String>) body.get("selectedDay"), (ReservedHours) body.get("repetitiveHour"), (Long) body.get("adminId"));
    }

    //Terem bezárása:
    @PostMapping("/closeRoomForADay")
    public ResponseEntity<Object> closeRoomForADay(@RequestBody Map<String, String> body){
       return reservationService.closeRoomForADay(body.get("date"), body.get("closeType"));
    }

    @PostMapping("/closeRoomBetweenPeriod")
    public ResponseEntity<Object> closeRoomBetweenPeriod(@RequestBody Map<String, String> body){
        return reservationService.closeRoomBetweenPeriod(body.get("startDate"), body.get("endDate"), body.get("closeType"));
    }

    @PostMapping("/closeByRepetitiveDates")
    public ResponseEntity<Object> closeByRepetitiveDates(@RequestBody Map<String, Object> body){
        return reservationService.closeByRepetitiveDates(body.get("startDate").toString(), body.get("endDate").toString(), body.get("closeType").toString(), (ArrayList<String>) body.get("selectedDay"));
    }

    @GetMapping("/reservedDate")
    public ResponseEntity<ReservedDates> getReservedDateByDate(@RequestParam("selectedDate") String selectedDateText){
        return reservationService.getReservedDateByDate(selectedDateText);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<Reservations>> getReservationsForAdmin(@RequestParam("startDate") String startDateText, @RequestParam("endDate") String endDateText, @RequestParam("startHour") int startHour, @RequestParam("endHour") int endHour){
        return reservationService.getReservationForAdmin(startDateText, endDateText, startHour, endHour);
    }
}