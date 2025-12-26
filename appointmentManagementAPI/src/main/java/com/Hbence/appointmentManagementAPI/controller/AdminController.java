package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.AdminDetails;
import com.Hbence.appointmentManagementAPI.entity.CloseReason;
import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.service.AdminService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "404", description = ""),
            @ApiResponse(responseCode = "415", description = ""),
            @ApiResponse(responseCode = "422", description = ""),
            @ApiResponse(responseCode = "500", description = ""),
    })
    @PostMapping("/reservation")
    public ResponseEntity<Object> makeAdminReservation(@RequestBody JsonNode requestBody) {
        return adminService.makeAdminReservation(requestBody.get("adminId").asLong(), requestBody.get("startHour").asInt(), requestBody.get("endHour").asInt(), requestBody.get("dateText").asText(null));
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "404", description = ""),
            @ApiResponse(responseCode = "415", description = ""),
            @ApiResponse(responseCode = "422", description = ""),
            @ApiResponse(responseCode = "500", description = ""),
    })
    @PostMapping("/reservationBetweenPeriod")
    public ResponseEntity<Object> makeReservationBetweenPeriod(@RequestBody JsonNode requestBody) {
        return adminService.makeReservationBetweenPeriod(requestBody.get("startDateText").asText(null), requestBody.get("endDateText").asText(null), requestBody.get("startHour").asInt(), requestBody.get("endHour").asInt(), requestBody.get("adminId").asLong());
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({

    })
    @PostMapping("/reservationRepetitive")
    public ResponseEntity<Object> makeReservationByRepetitiveDates(@RequestBody Map<String, Object> body) {
        return adminService.makeReservationByRepetitiveDates(body.get("startDateText").toString(), body.get("endDateText").toString(), (ArrayList<String>) body.get("selectedDay"), (Integer) body.get("startHour"), (Integer) body.get("endHour"), Long.valueOf(body.get("adminId").toString()));
    }

    //TEREM BEZARASA:
    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({

    })
    @PostMapping("/closeRoomForADay")
    public ResponseEntity<Object> closeRoomForADay(@RequestBody JsonNode requestBody) {
        return adminService.closeRoomForADay(requestBody.get("date").asText(), requestBody.get("closeReasonId").asInt());
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({

    })
    @PostMapping("/closeRoomBetweenPeriod")
    public ResponseEntity<Object> closeRoomBetweenPeriod(@RequestBody JsonNode requestBody) {
        return adminService.closeRoomBetweenPeriod(requestBody.get("startDate").asText(), requestBody.get("endDate").asText(), requestBody.get("closeReasonId").asInt());
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({

    })
    @PostMapping("/closeByRepetitiveDates")
    public ResponseEntity<Object> closeByRepetitiveDates(@RequestBody Map<String, Object> body) {
        return adminService.closeByRepetitiveDates(body.get("startDate").toString(), body.get("endDate").toString(), Integer.valueOf(body.get("closeReasonId").toString()), (ArrayList<String>) body.get("selectedDay"));
    }

    //CLOSEREASON:
    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @GetMapping("/closeReasons")
    public ResponseEntity<List<CloseReason>> getAllCloseReason() {
        return adminService.getAllCloseReason();
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({

    })
    @PostMapping("/makeCloseReasons")
    public ResponseEntity<Object> addCloseReason(@RequestBody CloseReason newCloseReason) {
        return adminService.addCloseReason(newCloseReason);
    }

    //FOGLALASOK VISSZASZERZESE AZ ADMIN FOGLALASHOZ
    @Operation(summary = "", description = "")
    @Parameters({
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
    })
    @ApiResponses({

    })
    @GetMapping("/intervallumCheck")
    public ResponseEntity<Object> getReservationsForAdminIntervallum(@RequestParam("startDateText") String startDateText, @RequestParam("endDateText") String endDateText, @RequestParam("startHour") Integer startHour, @RequestParam("endHour") Integer endHour) {
        return adminService.getReservationsForAdminIntervallum(startDateText, endDateText, startHour, endHour);
    }

    @Operation(summary = "", description = "")
    @Parameters({
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
    })
    @ApiResponses({

    })
    @GetMapping("/repetitiveCheck")
    public ResponseEntity<Object> checkReservationForRepetitive(@RequestParam("startDateText") String startDateText, @RequestParam("endDateText") String endDateText, @RequestParam("selectedDays") List<String> selectedDays, @RequestParam("startHour") Integer startHour, @RequestParam("endHour") Integer endHour) {
        return adminService.checkReservationForRepetitive(startDateText, endDateText, selectedDays, startHour, endHour);
    }

    @Operation(summary = "", description = "")
    @Parameters({
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
    })
    @ApiResponses({

    })
    @GetMapping("/reservationCheck")
    public ResponseEntity<Object> checkReservationForSimple(@RequestParam("dateText") String dateText, @RequestParam("startHour") Integer startHour, @RequestParam("endHour") Integer endHour) {
        return adminService.checkReservationForSimple(dateText, startHour, endHour);
    }

    //FOGLALASOK VISSZASZERZESE REPETITIVE ZARASHOZ
    @Operation(summary = "", description = "")
    @Parameters({
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
    })
    @ApiResponses({

    })
    @GetMapping("/intervallumCloseCheck")
    public ResponseEntity<Object> intervallumCloseCheck(@RequestParam("startDateText") String startDateText, @RequestParam("endDateText") String endDateText) {
        return adminService.intervallumCloseCheck(startDateText, endDateText);
    }

    @Operation(summary = "", description = "")
    @Parameters({
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
    })
    @ApiResponses({

    })
    @GetMapping("/repetitiveCloseCheck")
    public ResponseEntity<Object> repetitiveCloseCheck(@RequestParam("startDateText") String startDateText, @RequestParam("endDateText") String endDateText, @RequestParam("selectedDays") ArrayList<String> selectedDays) {
        return adminService.repetitiveCloseCheck(startDateText, endDateText, selectedDays);
    }

    //ADMINOK KEZELESE
    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", in = ParameterIn.PATH, required = true)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({

    })
    @PostMapping("/makeAdmin/{id}")
    public ResponseEntity<Object> makeAdmin(@PathVariable("id") Long id, @RequestBody AdminDetails newAdminDetails) {
        return adminService.makeAdmin(id, newAdminDetails);
    }

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @GetMapping("")
    public ResponseEntity<List<Users>> getAdminList() {
        return adminService.getAllAdmin();
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({

    })
    @PutMapping("/updateAdmin")
    public ResponseEntity<Object> updateAdmin(@RequestBody AdminDetails updatedDetails) {
        return adminService.updateAdmin(updatedDetails);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", in = ParameterIn.PATH, required = true)
    @ApiResponses({

    })
    @DeleteMapping("/deleteAdmin/{id}")
    public ResponseEntity<Object> deleteAdmin(@PathVariable("id") Long id) {
        return adminService.deleteAdmin(id);
    }

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @GetMapping("/shortList")
    public ResponseEntity<Object> getShortUsersList() {
        return adminService.getShortUsersList();
    }
}
