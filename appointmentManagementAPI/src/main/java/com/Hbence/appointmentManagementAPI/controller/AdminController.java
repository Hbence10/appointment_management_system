package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.AdminDetails;
import com.Hbence.appointmentManagementAPI.entity.CloseReason;
import com.Hbence.appointmentManagementAPI.entity.Reservations;
import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.service.AdminService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    //ADMIN FOGLALAS
    @Operation(summary = "Adminok foglalás tétele", description = "Adminok foglalás tétele")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A foglalás adatait tartalmazó object.", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "adminId", schema = @Schema(implementation = Long.class, description = "A foglalást készitő adminhoz tartozó id.")),
                    @SchemaProperty(name = "startHour", schema = @Schema(implementation = Integer.class, description = "A foglalás kezdete.")),
                    @SchemaProperty(name = "endHour", schema = @Schema(implementation = Integer.class, description = "A foglalás vége.")),
                    @SchemaProperty(name = "dateText", schema = @Schema(implementation = String.class, description = "A foglalás dátuma."))
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres foglalás", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező admin teszi a foglalást", content = @Content),
            @ApiResponse(responseCode = "415", description = "A kezdő óra előrébb van mint a végzés", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása requestBody nélkül vagy hiányos requestBody-val", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PostMapping("/reservation")
    public ResponseEntity<Object> makeAdminReservation(@RequestBody JsonNode requestBody) {
        return adminService.makeAdminReservation(requestBody.get("adminId").asLong(0), requestBody.get("startHour").asInt(0), requestBody.get("endHour").asInt(0), requestBody.get("dateText").asText(null));
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "startDateText", schema = @Schema(description = "", implementation = String.class)),
                    @SchemaProperty(name = "endDateText", schema = @Schema(description = "", implementation = String.class)),
                    @SchemaProperty(name = "startHour", schema = @Schema(description = "", implementation = Integer.class)),
                    @SchemaProperty(name = "endHour", schema = @Schema(description = "", implementation = Integer.class)),
                    @SchemaProperty(name = "adminId", schema = @Schema(description = "", implementation = Long.class)),
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres foglalások tétele", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező admin teszi a foglalást", content = @Content),
            @ApiResponse(responseCode = "415", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása requestBody nélkül vagy hiányos requestBody-val.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PostMapping("/reservationBetweenPeriod")
    public ResponseEntity<Object> makeReservationBetweenPeriod(@RequestBody JsonNode requestBody) {
        return adminService.makeReservationBetweenPeriod(requestBody.get("startDateText").asText(null), requestBody.get("endDateText").asText(null), requestBody.get("startHour").asInt(), requestBody.get("endHour").asInt(), requestBody.get("adminId").asLong());
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "", schema = @Schema(implementation = String.class, description = "")),
                    @SchemaProperty(name = "", schema = @Schema(implementation = String.class, description = "")),
                    @SchemaProperty(name = "", schema = @Schema(implementation = String.class, description = "")),
                    @SchemaProperty(name = "", schema = @Schema(implementation = String.class, description = "")),
                    @SchemaProperty(name = "", schema = @Schema(implementation = String.class, description = "")),
                    @SchemaProperty(name = "", schema = @Schema(implementation = String.class, description = "")),
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "", content = @Content),
            @ApiResponse(responseCode = "404", description = "", content = @Content),
            @ApiResponse(responseCode = "415", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content)
    })
    @PostMapping("/reservationRepetitive")
    public ResponseEntity<Object> makeReservationByRepetitiveDates(@RequestBody Map<String, Object> body) {
        return adminService.makeReservationByRepetitiveDates(body.get("startDateText").toString(), body.get("endDateText").toString(), (ArrayList<String>) body.get("selectedDay"), (Integer) body.get("startHour"), (Integer) body.get("endHour"), Long.valueOf(body.get("adminId").toString()));
    }

    //TEREM BEZARASA:
    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "", schema = @Schema(implementation = String.class, description = "")),
                    @SchemaProperty(name = "", schema = @Schema(implementation = Integer.class, description = ""))
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "", content = @Content),
            @ApiResponse(responseCode = "404", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content),
    })
    @PostMapping("/closeRoomForADay")
    public ResponseEntity<Object> closeRoomForADay(@RequestBody JsonNode requestBody) {
        return adminService.closeRoomForADay(requestBody.get("date").asText(), requestBody.get("closeReasonId").asInt());
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "", schema = @Schema(implementation = String.class)),
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "", content = @Content),
            @ApiResponse(responseCode = "404", description = "", content = @Content),
            @ApiResponse(responseCode = "415", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content),
    })
    @PostMapping("/closeRoomBetweenPeriod")
    public ResponseEntity<Object> closeRoomBetweenPeriod(@RequestBody JsonNode requestBody) {
        return adminService.closeRoomBetweenPeriod(requestBody.get("startDate").asText(), requestBody.get("endDate").asText(), requestBody.get("closeReasonId").asInt());
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schemaProperties = {
                    @SchemaProperty(name = "", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "", schema = @Schema(implementation = String.class)),
                    @SchemaProperty(name = "", schema = @Schema(implementation = String.class)),
            }
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "", content = @Content),
            @ApiResponse(responseCode = "404", description = "", content = @Content),
            @ApiResponse(responseCode = "415", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content),
    })
    @PostMapping("/closeByRepetitiveDates")
    public ResponseEntity<Object> closeByRepetitiveDates(@RequestBody Map<String, Object> body) {
        return adminService.closeByRepetitiveDates(body.get("startDate").toString(), body.get("endDate").toString(), Integer.valueOf(body.get("closeReasonId").toString()), (ArrayList<String>) body.get("selectedDay"));
    }

    //CLOSEREASON:
    @Operation(summary = "", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CloseReason.class))
            )),
            @ApiResponse(responseCode = "500", description = "", content = @Content)
    })
    @GetMapping("/closeReasons")
    public ResponseEntity<List<CloseReason>> getAllCloseReason() {
        return adminService.getAllCloseReason();
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = CloseReason.class, description = "")
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CloseReason.class)
            )),
            @ApiResponse(responseCode = "409", description = "", content = @Content),
            @ApiResponse(responseCode = "415", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content)
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
            @ApiResponse(responseCode = "200", description = "", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Reservations.class))
            )),
            @ApiResponse(responseCode = "415", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content),
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
            @ApiResponse(responseCode = "200", description = "", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Reservations.class))
            )),
            @ApiResponse(responseCode = "415", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content),
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
            @ApiResponse(responseCode = "200", description = "", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Reservations.class))
            )),
            @ApiResponse(responseCode = "415", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content),
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
            @ApiResponse(responseCode = "200", description = "", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Reservations.class))
            )),
            @ApiResponse(responseCode = "415", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content),
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
            @ApiResponse(responseCode = "200", description = "", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Reservations.class))
            )),
            @ApiResponse(responseCode = "415", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content),
    })
    @GetMapping("/repetitiveCloseCheck")
    public ResponseEntity<Object> repetitiveCloseCheck(@RequestParam("startDateText") String startDateText, @RequestParam("endDateText") String endDateText, @RequestParam("selectedDays") ArrayList<String> selectedDays) {
        return adminService.repetitiveCloseCheck(startDateText, endDateText, selectedDays);
    }

    //ADMINOK KEZELESE
    @Operation(summary = "", description = "")
    @Parameter(name = "", description = "", in = ParameterIn.PATH, required = true)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AdminDetails.class, description = "")
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Users.class)
            )),
            @ApiResponse(responseCode = "404", description = ""),
            @ApiResponse(responseCode = "409", description = ""),
            @ApiResponse(responseCode = "415", description = ""),
            @ApiResponse(responseCode = "422", description = ""),
            @ApiResponse(responseCode = "500", description = ""),
    })
    @PostMapping("/makeAdmin/{id}")
    public ResponseEntity<Object> makeAdmin(@PathVariable("id") Long id, @RequestBody AdminDetails newAdminDetails) {
        return adminService.makeAdmin(id, newAdminDetails);
    }

    @Operation(summary = "", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Users.class))
            ))
    })
    @GetMapping("")
    public ResponseEntity<List<Users>> getAdminList() {
        return adminService.getAllAdmin();
    }

    @Operation(summary = "", description = "")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AdminDetails.class)
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AdminDetails.class)
            )),
            @ApiResponse(responseCode = "404", description = "", content = @Content),
            @ApiResponse(responseCode = "415", description = "", content = @Content),
            @ApiResponse(responseCode = "422", description = "", content = @Content),
            @ApiResponse(responseCode = "500", description = "", content = @Content),
    })
    @PutMapping("/updateAdmin")
    public ResponseEntity<Object> updateAdmin(@RequestBody AdminDetails updatedDetails) {
        return adminService.updateAdmin(updatedDetails);
    }

    @Operation(summary = "Admin törlése", description = "Admin törlése id alapján")
    @Parameter(name = "id", description = "", in = ParameterIn.PATH, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés", content = @Content),
            @ApiResponse(responseCode = "404", description = "Nem létező admin törlése.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba", content = @Content),
    })
    @DeleteMapping("/deleteAdmin/{id}")
    public ResponseEntity<Object> deleteAdmin(@PathVariable("id") Long id) {
        return adminService.deleteAdmin(id);
    }

    @Operation(summary = "Felhasználók röviditett listája", description = "Felhasználók röviditett listájának lekérdezése. A lista csak a felhasználók nevét és az ő hozzájuk tartozó id-t fogja tartalmazni.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "", content = @Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @SchemaProperty(name = "id", schema = @Schema(implementation = Long.class, description = "Az adott felhasználóhoz tartozó id.")),
                            @SchemaProperty(name = "username", schema = @Schema(implementation = String.class, description = "Az adott felhasználóhoz tartozó felhasználónév")),
                    }
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba", content = @Content)
    })
    @GetMapping("/shortList")
    public ResponseEntity<Object> getShortUsersList() {
        return adminService.getShortUsersList();
    }
}
