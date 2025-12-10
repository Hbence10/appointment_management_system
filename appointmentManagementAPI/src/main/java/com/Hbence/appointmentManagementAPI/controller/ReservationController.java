package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.service.ReservationService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Reservations>> getReservationByUserId(@PathVariable("id") Long id) {
        return reservationService.getReservationByUserId(id);
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @GetMapping("/reservedDates")
    public ResponseEntity<Object> getReservationBetweenIntervallum(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return reservationService.getReservationBetweenIntervallum(startDate, endDate);
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @GetMapping("/reservedHours")
    public ResponseEntity<List<ReservedHours>> getReservedHoursByDay(@RequestParam("selectedDay") String wantedDateDay) {
        return reservationService.getReservedHoursByDay(wantedDateDay);
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @GetMapping("/date/{wantedDate}")
    public ResponseEntity<List<Reservations>> getReservationsByDate(@PathVariable("wantedDate") String wantedDate) {
        return reservationService.getReservationByDate(wantedDate);
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @PostMapping("/makeReservation")
    public ResponseEntity<Object> makeReservation(@RequestBody Reservations newReservation) {
        return reservationService.makeReservation(newReservation);
    }

    //Foglalas lemondasa
    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @PatchMapping("/cancel/{id}")
    public ResponseEntity<Reservations> cancelReservation(@PathVariable("id") Long id, @RequestBody Users canceledBy) {
        return reservationService.cancelReservation(id, canceledBy);
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @PostMapping("/getByEmailAndVCode")
    public ResponseEntity<Object> getReservationByEmailAndVCode(@RequestBody JsonNode requestBody){
        return reservationService.getReservationByEmailAndVCode(requestBody.get("email").asText(), requestBody.get("vCode").asText());
    }

    @Operation(summary = "", description = "")
    @Parameters({

    })
    @ApiResponses({

    })
    @GetMapping("/reservedDate")
    public ResponseEntity<ReservedDates> getReservedDateByDate(@RequestParam("selectedDate") String selectedDateText){
        return reservationService.getReservedDateByDate(selectedDateText);
    }
}