package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.service.AdminService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    //ADMIN FOGLALAS
    @PostMapping("/reservation")
    public ResponseEntity<Object> makeAdminReservation(@RequestBody JsonNode requestBody){
        return adminService.makeAdminReservation(requestBody.get("adminId").asLong(), requestBody.get("startHour").asInt(), requestBody.get("endHour").asInt(), requestBody.get("dateText").asText());
    }

    @PostMapping("/reservationBetweenPeriod")
    public ResponseEntity<Object> makeReservationBetweenPeriod(@RequestBody JsonNode requestBody){
        return adminService.makeReservationBetweenPeriod(requestBody.get("startDateText").asText(), requestBody.get("endDateText").asText(), requestBody.get("startHour").asInt(), requestBody.get("endHour").asInt() ,requestBody.get("adminId").asLong());
    }

    @PostMapping("/reservationRepetitive")
    public ResponseEntity<Object> makeReservationByRepetitiveDates(@RequestBody Map<String, Object> body){
        return adminService.makeReservationByRepetitiveDates(body.get("startDateText").toString(), body.get("endDateText").toString(), (ArrayList<String>) body.get("selectedDay"), (Integer) body.get("startHour"), (Integer) body.get("endHour"), Long.valueOf(body.get("adminId").toString()));
    }

    //TEREM BEZARASA:
    @PostMapping("/closeRoomForADay")
    public ResponseEntity<Object> closeRoomForADay(@RequestBody JsonNode requestBody){
        return adminService.closeRoomForADay(requestBody.get("date").asText(), requestBody.get("closeReasonId").asInt());
    }

    @PostMapping("/closeRoomBetweenPeriod")
    public ResponseEntity<Object> closeRoomBetweenPeriod(@RequestBody JsonNode requestBody){
        return adminService.closeRoomBetweenPeriod(requestBody.get("startDate").asText(), requestBody.get("endDate").asText(), requestBody.get("closeReasonId").asInt());
    }

    @PostMapping("/closeByRepetitiveDates")
    public ResponseEntity<Object> closeByRepetitiveDates(@RequestBody Map<String, Object> body){
        return adminService.closeByRepetitiveDates(body.get("startDate").toString(), body.get("endDate").toString(), Integer.valueOf(body.get("closeReasonId").toString()), (ArrayList<String>) body.get("selectedDay"));
    }

    //CLOSEREASON:
    @GetMapping("/closeReasons")
    public ResponseEntity<List<CloseReason>> getAllCloseReason(){
        return adminService.getAllCloseReason();
    }

    @PostMapping("/makeCloseReasons")
    public ResponseEntity<Object> addCloseReason(@RequestBody CloseReason newCloseReason){
        return adminService.addCloseReason(newCloseReason);
    }

    //FOGLALASOK VISSZASZERZESE AZ ADMIN FOGLALASHOZ
    @GetMapping("/intervallumCheck")
    public ResponseEntity<Object> getReservationsForAdminIntervallum(@RequestParam("startDateText") String startDateText, @RequestParam("endDateText") String endDateText, @RequestParam("startHour") Integer startHour, @RequestParam("endHour") Integer endHour) {
        return adminService.getReservationsForAdminIntervallum(startDateText, endDateText, startHour, endHour);
    }

    @GetMapping("/repetitiveCheck")
    public ResponseEntity<Object> checkReservationForRepetitive(@RequestParam("startDateText") String startDateText, @RequestParam("endDateText") String endDateText, @RequestParam("selectedDays") List<String> selectedDays, @RequestParam("startHour") Integer startHour, @RequestParam("endHour") Integer endHour){
        return adminService.checkReservationForRepetitive(startDateText, endDateText, selectedDays, startHour, endHour);
    }

    @GetMapping("/reservationCheck")
    public ResponseEntity<Object> checkReservationForSimple(@RequestParam("dateText") String dateText, @RequestParam("startHour") Integer startHour, @RequestParam("endHour") Integer endHour){
        return adminService.checkReservationForSimple(dateText, startHour, endHour);
    }

    //FOGLALASOK VISSZASZERZESE REPETITIVE ZARASHOZ
    @GetMapping("/intervallumCloseCheck")
    public ResponseEntity<List<Reservations>> intervallumCloseCheck(@RequestParam("startDateText") String startDateText, @RequestParam("endDateText") String endDateText){
        return adminService.intervallumCloseCheck(startDateText, endDateText);
    }

    @GetMapping("/repetitiveCloseCheck")
    public ResponseEntity<Object> repetitiveCloseCheck(@RequestParam("startDateText") String startDateText, @RequestParam("endDateText") String endDateText, @RequestParam("selectedDays") ArrayList<String> selectedDays){
        return adminService.repetitiveCloseCheck(startDateText, endDateText, selectedDays);
    }

    //ADMINOK KEZELESE
    @PostMapping("/makeAdmin/{id}")
    public ResponseEntity<Users> makeAdmin(@PathVariable("id") Long id, @RequestBody AdminDetails newAdminDetails) {
        return adminService.makeAdmin(id, newAdminDetails);
    }

    @GetMapping("")
    public ResponseEntity<List<Users>> getAdminList() {
        return adminService.getAllAdmin();
    }

    @PutMapping("/updateAdmin")
    public ResponseEntity<Object> updateAdmin(@RequestBody AdminDetails updatedDetails) {
        return adminService.updateAdmin(updatedDetails);
    }

    @DeleteMapping("/deleteAdmin/{id}")
    public ResponseEntity<Object> deleteAdmin(@PathVariable("id") Long id) {
        return adminService.deleteAdmin(id);
    }
}
