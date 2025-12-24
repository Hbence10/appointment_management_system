package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.PaymentMethods;
import com.Hbence.appointmentManagementAPI.entity.PhoneCountryCode;
import com.Hbence.appointmentManagementAPI.entity.ReservationType;
import com.Hbence.appointmentManagementAPI.service.ReservationStuffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservationStuff")
@RequiredArgsConstructor
public class ReservationStuffController {

    private final ReservationStuffService reservationStuffService;

    @Operation(summary = "", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "500", description = "", content = @Content)
    })
    @GetMapping("/getReservationType")
    public ResponseEntity<List<ReservationType>> getAllReservationTypes() {
        return reservationStuffService.getAllReservationType();
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "409", description = "", content = @Content),
            @ApiResponse(responseCode = "415", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content),
    })
    @PostMapping("/addReservationType")
    public ResponseEntity<Object> addNewReservationType(@RequestBody ReservationType newReservationType) {
        return reservationStuffService.addNewReservationType(newReservationType);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", in = ParameterIn.PATH, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "404", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content),

    })
    @DeleteMapping("/deleteReservationType/{id}")
    public ResponseEntity<String> deleteReservationType(@PathVariable("id") Long id) {
        return reservationStuffService.deleteReservationType(id);
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "404", description = "", content = @Content),
            @ApiResponse(responseCode = "409", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content),
    })
    @PutMapping("/updateReservationType")
    public ResponseEntity<Object> updateReservationType(@RequestBody ReservationType updatedReservationType) {
        return reservationStuffService.updateReservationType(updatedReservationType);
    }

    //Fizetesi modszerek
    @Operation(summary = "", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "500", description = "", content = @Content)
    })
    @GetMapping("/paymentMethods")
    public ResponseEntity<List<PaymentMethods>> getAllPaymentMethod() {
        return reservationStuffService.getAllPaymentMethod();
    }

    //Telefonszam:
    @Operation(summary = "", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = ""),
            @ApiResponse(responseCode = "500", description = "", content = @Content)
    })
    @GetMapping("/phoneCodes")
    public ResponseEntity<List<PhoneCountryCode>> getAllPhoneCode() {
        return reservationStuffService.getAllPhoneCode();
    }
}
