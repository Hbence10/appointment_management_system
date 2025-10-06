package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.PaymentMethods;
import com.Hbence.appointmentManagementAPI.entity.PhoneCountryCode;
import com.Hbence.appointmentManagementAPI.entity.ReservationType;
import com.Hbence.appointmentManagementAPI.service.ReservationStuffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservationStuff")
@RequiredArgsConstructor
public class ReservationStuffController {

    private final ReservationStuffService reservationStuffService;

    @GetMapping("/getReservationType")
    public ResponseEntity<List<ReservationType>> getAllReservationTypes() {
        return reservationStuffService.getAllReservationType();
    }

    @PostMapping("/addReservationType")
    public ResponseEntity<ReservationType> addNewReservationType(@RequestBody ReservationType newReservationType) {
        return reservationStuffService.addNewReservationType(newReservationType);
    }

    @DeleteMapping("/deleteReservationType/{id}")
    public ResponseEntity<String> deleteReservationType(@PathVariable("id") Long id) {
        return reservationStuffService.deleteReservationType(id);
    }

    @PutMapping("/updateReservationType")
    public ResponseEntity<ReservationType> updateReservationType(@RequestBody ReservationType updatedReservationType) {
        return reservationStuffService.updateReservationType(updatedReservationType);
    }

    //Fizetesi modszerek
    @GetMapping("/paymentMethods")
    public ResponseEntity<List<PaymentMethods>> getAllPaymentMethod() {
        return reservationStuffService.getAllPaymentMethod();
    }

    //Telefonszam:
    @GetMapping("/phoneCodes")
    public ResponseEntity<List<PhoneCountryCode>> getAllPhoneCode() {
        return reservationStuffService.getAllPhoneCode();
    }
}
