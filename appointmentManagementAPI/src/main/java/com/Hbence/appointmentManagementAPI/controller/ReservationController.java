package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Reservations;
import com.Hbence.appointmentManagementAPI.entity.ReservedDates;
import com.Hbence.appointmentManagementAPI.entity.ReservedHours;
import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.service.ReservationService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    //Foglalasok
    @Operation(summary = "Foglalások visszaszerzése felhasználó alapján", description = "Foglalások visszaszerzése felhasználó alapján")
    @Parameter(name = "id", description = "A felhasználóhoz tartozó id.", in = ParameterIn.PATH, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Reservations.class))
            )),
            @ApiResponse(responseCode = "404", description = "A keresett felhasználó nem létezik."),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter nélkül."),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.")
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Reservations>> getReservationByUserId(@PathVariable("id") Long id) {
        return reservationService.getReservationByUserId(id);
    }

    @Operation(summary = "Lefoglalt dátumok két dátum között", description = "Az összes lefoglalt dátum lekérdezése az adott két dátum között.")
    @Parameters({
            @Parameter(name = "startDate", description = "Az intervallum kezdete", in = ParameterIn.QUERY, required = true, example = ""),
            @Parameter(name = "endDate", description = "Az intervallum vége", in = ParameterIn.QUERY, required = true, example = "")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ReservedDates.class))
            )),
            @ApiResponse(responseCode = "415", description = "A kezdő dátum később van mint a vég dátum.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása requestBody nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @GetMapping("/reservedDates")
    public ResponseEntity<Object> getReservedDatesBetweenIntervallum(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return reservationService.getReservationBetweenIntervallum(startDate, endDate);
    }

    //    VISSZA VAN A DOKUMENTÁLÁS
    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true)
    @ApiResponses({

    })
    @GetMapping("/reservedHours")
    public ResponseEntity<List<ReservedHours>> getReservedHoursByDay(@RequestParam("selectedDay") String wantedDateDay) {
        return reservationService.getReservedHoursByDay(wantedDateDay);
    }

    @Operation(summary = "Foglalás(ok) dátum alapján", description = "Foglalás visszaszerzése dátum alapján")
    @Parameter(name = "wantedDate", description = "A kért dátum", in = ParameterIn.QUERY, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Reservations.class))
            )),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása requestBody nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.")
    })
    @GetMapping("/date/{wantedDate}")
    public ResponseEntity<List<Reservations>> getReservationsByDate(@PathVariable("wantedDate") String wantedDate) {
        return reservationService.getReservationByDate(wantedDate);
    }

    @Operation(summary = "Foglalás készítése", description = "Foglalás készítése")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Reservations.class, description = "Az új foglalásnak az object-je")
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres foglalás tétele", content = @Content),
            @ApiResponse(responseCode = "404", description = "A foglaláshoz csatolt fiók vagy hivásazonosító kód nem létezik az adatbázisban. userNotFound: A fiók nem található, phoneCountryCodeNotFound: hivás azonositó nem található", content = @Content),
            @ApiResponse(responseCode = "415", description = "Felépitésében helytelen adatok megadása. invalidObject: a foglalás object-jének az id-ja nem egyenlő null-lal, invalidEmail: az e-mail felépitésben nem megfelelő, invalidPhoneNumber: a telefonszám felépitésében nem megfelelő", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása requestBody nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @PostMapping("/makeReservation")
    public ResponseEntity<Object> makeReservation(@RequestBody Reservations newReservation) {
        return reservationService.makeReservation(newReservation);
    }

    //Foglalas lemondasa
    @Operation(summary = "Foglalás lemondása", description = "Foglalás lemondása")
    @Parameter(name = "id", description = "A foglaláshoz tartozó id.", in = ParameterIn.PATH, required = true)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A foglalást lemondó felhasználó object-je.", required = false, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Users.class)
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lemondás", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Reservations.class, description = "A már lemondott foglalás")
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező foglalás lemondása/ha van hozzáadva felhasználó akkor nem létező felhasználó hozzáadása. reservationNotFound: Nem létező foglalás, userNotFound: Nem létező felhasználó", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba", content = @Content)

    })
    @PatchMapping("/cancel/{id}")
    public ResponseEntity<Object> cancelReservation(@PathVariable("id") Long id, @RequestBody(required = false) Users canceledBy) {
        return reservationService.cancelReservation(id, canceledBy);
    }

    @Operation(summary = "Foglalás visszaszerzése", description = "Foglalás visszaszerzése e-mail & hitelesitő kód által.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A kivánt foglaláshoz tartalmazó hitelesitő kód és e-mail cím.", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "email", schema = @Schema(implementation = String.class, description = "A foglaláshoz tartozó e-mail.")),
                    @SchemaProperty(name = "vCode", schema = @Schema(implementation = String.class, description = "A foglaláshoz tartozó hitelesitő kód."))
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Reservations.class)
            )),
            @ApiResponse(responseCode = "404", description = "A megadott e-mail cím vagy a keresett foglalás nincs jelen az adatbázisban. emailNotFound: Nincs ilyen e-maillel foglalás, reservationNotFound: Nincs ilyen foglalás amelyhez a megadott e-mail cím és hitelesitő kód tartozik.", content = @Content),
            @ApiResponse(responseCode = "415", description = "Felépitésben téves e-mail cím", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása requestBody nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @PostMapping("/getByEmailAndVCode")
    public ResponseEntity<Object> getReservationByEmailAndVCode(@RequestBody JsonNode requestBody) {
        return reservationService.getReservationByEmailAndVCode(requestBody.get("email").asText(null), requestBody.get("vCode").asText(null));
    }

    @Operation(summary = "ReservedDate lekérdezése", description = "ReservedDate lekérdezése dátum alapján.")
    @Parameter(name = "selectedDate", description = "A kiválasztott dátum.", in = ParameterIn.QUERY, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReservedDates.class, description = "A keresett ReservedDate object. Ha nem létezik")
            )),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása paraméter nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/reservedDate")
    public ResponseEntity<ReservedDates> getReservedDateByDate(@RequestParam("selectedDate") String selectedDateText) {
        return reservationService.getReservedDateByDate(selectedDateText);
    }
}