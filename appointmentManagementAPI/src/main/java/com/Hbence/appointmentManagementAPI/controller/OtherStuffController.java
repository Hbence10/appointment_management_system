package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Details;
import com.Hbence.appointmentManagementAPI.entity.History;
import com.Hbence.appointmentManagementAPI.entity.OpeningDetails;
import com.Hbence.appointmentManagementAPI.entity.Rules;
import com.Hbence.appointmentManagementAPI.service.OtherStuffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OtherStuffController {

    private final OtherStuffService otherStuffService;

    //Szabalyzat:
    @Operation(summary = "Szabályzat lekérdezése", description = "Szabályzat lekérdezése")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Rules.class)
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/rule")
    public ResponseEntity<Rules> getRule() {
        return otherStuffService.getRule();
    }

    @Operation(summary = "Szabályzat frissitése", description = "Szabályzat frissitése")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A frissitett szabályzat object-je.", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Rules.class, description = "A frissitett szabályzat object-je.")
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Rules.class, description = "A frissitett szabályzat object-je.")
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező id-jú object megadása", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása requestBody nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PutMapping("/rule/update")
    public ResponseEntity<Rules> updateRule(@RequestBody Rules updatedRule) {
        return otherStuffService.updateRules(updatedRule);
    }

    //History
    @Operation(summary = "Előzmények lekérdezése", description = "Előzmények lekérdezése")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = History.class))
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/history")
    public ResponseEntity<List<History>> getHistory() {
        return otherStuffService.getHistory();
    }

    //Adatok
    @Operation(summary = "Adatok lekérdezése", description = "A footerhez szükséges adatok lekérdezése.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Details.class)
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/details")
    public ResponseEntity<Details> getDetails() {
        return otherStuffService.getDetails();
    }

    @Operation(summary = "Adatok frissitése", description = "A footerhez szükséges adatok frissitése")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A frissitett adatok object-je", required = true, content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Details.class, description = "A frissitett adatok object-je")
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Details.class, description = "A frissitett adatok object-je")
            )),
            @ApiResponse(responseCode = "404", description = "Rossz id-jú object megadása", content = @Content),
            @ApiResponse(responseCode = "415", description = "Felépitésben rossz e-mail cím vagy telefonszám(ok) megadása. ", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása requestBody nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta error.", content = @Content)
    })
    @PutMapping("/details/update")
    public ResponseEntity<Object> updateDetails(@RequestBody Details updatedDetails) {
        return otherStuffService.updateDetails(updatedDetails);
    }

    @Operation(summary = "Nyitvatartás lekérdezése", description = "Nyitvatartás lekérdezése")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres lekérdezés", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = OpeningDetails.class))
            )),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @GetMapping("/openingDetails")
    public ResponseEntity<List<OpeningDetails>> getOpeningDetails() {
        return otherStuffService.getOpeningDetails();
    }

    @Operation(summary = "Nyitvatartás frissitése", description = "Nyitvatartás frissitése")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A frissitett nyitvatartás lista", required = true, content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = OpeningDetails.class))
    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = OpeningDetails.class))
            )),
            @ApiResponse(responseCode = "404", description = "Nem létező nap megadása egy adott nyitvatartás object-nél.", content = @Content),
            @ApiResponse(responseCode = "415", description = "A nyitási idő később van mint a zárási idő.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása requestBody nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content),
    })
    @PutMapping("/openingDetails/update")
    public ResponseEntity<List<OpeningDetails>> updateOpeningDetails(@RequestBody List<OpeningDetails> updatedOpeningDetails) {
        return otherStuffService.updateOpeningDetails(updatedOpeningDetails);
    }
}
