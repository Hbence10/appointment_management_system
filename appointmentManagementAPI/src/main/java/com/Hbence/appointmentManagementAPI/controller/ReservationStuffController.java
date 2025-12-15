package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.PaymentMethods;
import com.Hbence.appointmentManagementAPI.entity.PhoneCountryCode;
import com.Hbence.appointmentManagementAPI.entity.ReservationType;
import com.Hbence.appointmentManagementAPI.service.ReservationStuffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

    })
    @GetMapping("/getReservationType")
    public ResponseEntity<List<ReservationType>> getAllReservationTypes() {
        return reservationStuffService.getAllReservationType();
    }

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @PostMapping("/addReservationType")
    public ResponseEntity<Object> addNewReservationType(@RequestBody ReservationType newReservationType) {
        return reservationStuffService.addNewReservationType(newReservationType);
    }

    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", in = ParameterIn.PATH, required = true)
    @ApiResponses({

    })
    @DeleteMapping("/deleteReservationType/{id}")
    public ResponseEntity<String> deleteReservationType(@PathVariable("id") Long id) {
        return reservationStuffService.deleteReservationType(id);
    }

    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @PutMapping("/updateReservationType")
    public ResponseEntity<Object> updateReservationType(@RequestBody ReservationType updatedReservationType) {
        return reservationStuffService.updateReservationType(updatedReservationType);
    }

    //Fizetesi modszerek
    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @GetMapping("/paymentMethods")
    public ResponseEntity<List<PaymentMethods>> getAllPaymentMethod() {
        return reservationStuffService.getAllPaymentMethod();
    }

    //Telefonszam:
    @Operation(summary = "", description = "")
    @ApiResponses({

    })
    @GetMapping("/phoneCodes")
    public ResponseEntity<List<PhoneCountryCode>> getAllPhoneCode() {
        return reservationStuffService.getAllPhoneCode();
    }
}
