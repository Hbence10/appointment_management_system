package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.PaymentMethods;
import com.Hbence.appointmentManagementAPI.entity.PhoneCountryCode;
import com.Hbence.appointmentManagementAPI.entity.ReservationType;
import com.Hbence.appointmentManagementAPI.service.ReservationStuffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(summary = "Foglalási tipusok lekérdezése", description = "Foglalási tipusok lekérdezése")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ReservationType.class))
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/getReservationType")
    public ResponseEntity<List<ReservationType>> getAllReservationTypes() {
        return reservationStuffService.getAllReservationType();
    }

    @Operation(summary = "Foglalás tipus hozzáadása", description = "Foglalás tipus hozzáadása")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Az új foglalás tipus object-je", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ReservationType.class, description = "Az új foglalás tipus object-je")
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres hozzáadás", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReservationType.class, description = "Az új foglalás tipus object-je")
            )),
            @ApiResponse(responseCode = "409", description = "Duplikált tipus név", content = @Content),
            @ApiResponse(responseCode = "415", description = "A megadott object id-ja nem volt null", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása requestBody nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PostMapping("/addReservationType")
    public ResponseEntity<Object> addNewReservationType(@RequestBody ReservationType newReservationType) {
        return reservationStuffService.addNewReservationType(newReservationType);
    }

    @Operation(summary = "Foglalás tipusa törlése", description = "Foglalás tipus törlés id alapján")
    @Parameter(name = "id", description = "A foglalás tipushoz tartozó id.", in = ParameterIn.PATH, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező foglalás tipus törlése", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),

    })
    @DeleteMapping("/deleteReservationType/{id}")
    public ResponseEntity<String> deleteReservationType(@PathVariable("id") Long id) {
        return reservationStuffService.deleteReservationType(id);
    }

    @Operation(summary = "Foglalási tipus frissitése", description = "Foglalási tipus frissitése")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A frissitett foglalási tipus object-je", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ReservationType.class, description = "A frissitett foglalási tipus object-je")
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReservationType.class, description = "A frissitett foglalási tipus object-je")
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező foglalási tipus frissitése", content = @Content),
            @ApiResponse(responseCode = "409", description = "Duplikált adat regisztrálása a foglalás tipus neve miatt", content = @Content),
            @ApiResponse(responseCode = "415", description = "Az adott object felépitésben nem megfelelő, az id-nak null-nak kell lennie", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása requestBody nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba", content = @Content),
    })
    @PutMapping("/updateReservationType")
    public ResponseEntity<Object> updateReservationType(@RequestBody ReservationType updatedReservationType) {
        return reservationStuffService.updateReservationType(updatedReservationType);
    }

    //Fizetesi modszerek
    @Operation(summary = "Fizetési módszerek lekérdezése", description = "Fizetési módszerek lekérdezése")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PaymentMethods.class))
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/paymentMethods")
    public ResponseEntity<List<PaymentMethods>> getAllPaymentMethod() {
        return reservationStuffService.getAllPaymentMethod();
    }

    //Telefonszam:
    @Operation(summary = "Hívás azonosítók lekérdezése", description = "Hívás azonosítók lekérdezése")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PhoneCountryCode.class))
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/phoneCodes")
    public ResponseEntity<List<PhoneCountryCode>> getAllPhoneCode() {
        return reservationStuffService.getAllPhoneCode();
    }
}
