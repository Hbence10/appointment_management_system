package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.AdminDetails;
import com.Hbence.appointmentManagementAPI.entity.ReservedHours;
import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.service.AdminService;
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
    public ResponseEntity<Object> makeAdminReservation(@RequestBody Map<String, Object> bodyObject){
        return adminService.makeAdminReservation( Long.valueOf(bodyObject.get("adminId").toString()), (Integer) bodyObject.get("startHour"), (Integer) bodyObject.get("endHour"), bodyObject.get("dateText").toString());
    }

    @PostMapping("/reservationBetweenPeriod")
    public ResponseEntity<Object> makeReservationBetweenPeriod(@RequestBody Map<String, Object> body){
        return adminService.makeReservationBetweenPeriod(body.get("startDate").toString(), body.get("endDate").toString(), (Integer) body.get("startHour"), (Integer) body.get("endHour") ,Long.valueOf(body.get("adminId").toString()));
    }

    @PostMapping("/reservationRepetitive")
    public ResponseEntity<Object> makeReservationByRepetitiveDates(@RequestBody Map<String, Object> body){
        return adminService.makeReservationByRepetitiveDates(body.get("startDate").toString(), body.get("endDate").toString(), (ArrayList<String>) body.get("selectedDay"), (Integer) body.get("startHour"), (Integer) body.get("endHour"), Long.valueOf(body.get("adminId").toString()));
    }

    //TEREM BEZARASA:
    @PostMapping("/closeRoomForADay")
    public ResponseEntity<Object> closeRoomForADay(@RequestBody Map<String, String> body){
        return adminService.closeRoomForADay(body.get("date"), body.get("closeType"));
    }

    @PostMapping("/closeRoomBetweenPeriod")
    public ResponseEntity<Object> closeRoomBetweenPeriod(@RequestBody Map<String, String> body){
        return adminService.closeRoomBetweenPeriod(body.get("startDate"), body.get("endDate"), body.get("closeType"));
    }

    @PostMapping("/closeByRepetitiveDates")
    public ResponseEntity<Object> closeByRepetitiveDates(@RequestBody Map<String, Object> body){
        return adminService.closeByRepetitiveDates(body.get("startDate").toString(), body.get("endDate").toString(), body.get("closeType").toString(), (ArrayList<String>) body.get("selectedDay"));
    }

    //FOGLALASOK VISSZASZERZESE AZ ADMIN FOGLALASHOZ
    @GetMapping("/intervallumCheck")
    public ResponseEntity<Object> getReservationsForAdminIntervallum(@RequestParam("startDateText") String startDateText, @RequestParam("endDateText") String endDateText, @RequestParam("startHour") Integer startHour, @RequestParam("endHour") Integer endHour) {
        return adminService.getReservationsForAdminIntervallum(startDateText, endDateText, startHour, endHour);
    }

    @GetMapping("/repetitiveCheck")
    public ResponseEntity<Object> checkReservationForRepetitive(@RequestParam("startDateText") String startDateText, @RequestParam("endDateText") String endDateText, @RequestParam("selectedDays") ArrayList<String> selectedDays, @RequestParam("startHour") Integer startHour, @RequestParam("endHour") Integer endHour){
        return adminService.checkReservationForRepetitive(startDateText, endDateText, selectedDays, startHour, endHour);
    }

    @GetMapping("/reservationCheck")
    public ResponseEntity<Object> checkReservationForSimple(@RequestParam("dateText") String dateText, @RequestParam("startHour") Integer startHour, @RequestParam("endHour") Integer endHour){
        return adminService.checkReservationForSimple(dateText, startHour, endHour);
    }

    //FOGLALASOK VISSZASZERZESE REPETITIVE ZARASHOZ
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
