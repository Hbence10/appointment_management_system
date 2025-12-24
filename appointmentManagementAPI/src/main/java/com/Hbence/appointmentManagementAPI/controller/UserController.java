package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Sima bejelentkezés", description = "Egy felhasználó felhasználónév & jelszó általi bejelentkezés.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "description",
            content = @Content(
                    mediaType = "application/json",
                    schemaProperties = {
                            @SchemaProperty(name = "username", schema = @Schema(implementation = String.class)),
                            @SchemaProperty(name = "password", schema = @Schema(implementation = String.class))
                    },
                    examples = @ExampleObject(value = "")
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200", description = "Sikeres bejelentkezés.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Users.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Sikertelen bejelentkezés, téves felhasználónév vagy email cím.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása requestBody nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server által okozott hiba.", content = @Content),
    })
    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody JsonNode loginBody) {
        return userService.login(loginBody.get("username").asText(), loginBody.get("password").asText());
    }

    @Operation(summary = "Regisztráció", description = "Új felhasználó regisztrálása.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, useParameterTypeSchema = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres regisztráció."),
            @ApiResponse(responseCode = "409", description = "Olyan e-mail címmel vagy felhasználónévvel regisztrált a felhasználó, amely már létezik az adatbázisban.", content = @Content),
            @ApiResponse(responseCode = "415", description = "Felépitésben helytelen e-mail cím vagy felhasználónév", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása request body nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server által okozott hiba.", content = @Content),
    })
//    @ExceptionHandler(DataIntegrityViolationException.class)
    @PostMapping("/register")
    public ResponseEntity<Object> registration(@RequestBody Users newUser) {
        return userService.register(newUser);
    }

    @Operation(summary = "Felhasználó frissitése.", description = "Felhasználó frissitése")
    @Parameter(name = "id", description = "A felhasználóhoz tartozó id.", required = true, in = ParameterIn.PATH)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, useParameterTypeSchema = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités"),
            @ApiResponse(responseCode = "404", description = "Nem létező felhasználó frissitése", content = @Content),
            @ApiResponse(responseCode = "409", description = "Már foglalt e-cimre vagy felhasználónévre való frissités", content = @Content),
            @ApiResponse(responseCode = "415", description = "Felépitésben helytelen email cim.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása parameter vagy request body nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server által okozott hiba.", content = @Content),
    })
    @PatchMapping("/updateUser/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") Long id, @RequestBody JsonNode requestBody) {
        return userService.updateUser(id, requestBody.get("email").asText(), requestBody.get("username").asText());
    }

    @Operation(summary = "Felhasználó törlése", description = "Felhasználó törlése id alapján")
    @Parameter(name = "id", description = "A felhasználóhoz tartozó id.", in = ParameterIn.PATH, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés"),
            @ApiResponse(responseCode = "404", description = "Nem létező felhasználó törlése", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása parameter nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server által okozott hiba.", content = @Content),
    })
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

    //PFP
    @Operation(summary = "Profilkép csere.", description = "Profilkép csere.")
    @Parameters({
            @Parameter(name = "id", description = "A felhasználóhoz tartozó id.", required = true, in = ParameterIn.PATH),
            @Parameter(name = "pfpImg", description = "A felhasználó által kiválasztott kép.", required = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres profilkép csere."),
            @ApiResponse(responseCode = "404", description = "Nem létező profil", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása parameter nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba", content = @Content),
    })
    @PatchMapping("/changePfp/{id}")
    public ResponseEntity<Object> changePfp(@PathVariable("id") Long id, @RequestParam("pfpImg") MultipartFile file) {
        return userService.changePfp(id, file);
    }

    //password-reset
    @Operation(summary = "Hitelesitő kód küldés", description = "A megadott email-re a jelszó frissitéshez szükéseg hitelesitő kód küldése.")
    @Parameter(name = "email", description = "A felhasználó által megadott email a hitelesitő kód küldéséhez.", required = true, in = ParameterIn.QUERY)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kód küldése."),
            @ApiResponse(responseCode = "404", description = "Olyan email címet adott meg a felhasználó amely nem szerepel az adatbázisban.", content = @Content),
            @ApiResponse(responseCode = "415", description = "Felépitésben helytelen email cimet adott meg a felhasználó.", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter nélkül.", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server által okozott hiba.", content = @Content),
    })
    @GetMapping("/getVerificationCode")
    public ResponseEntity<String> getVerificationCode(@RequestParam("email") String email) {
        return userService.getVerificationCode(email);
    }

    @Operation(summary = "Hitelesitő kód ellenőrzése", description = "Hitelesitő kód ellenőrzése")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres ellenőrzés"),
            @ApiResponse(responseCode = "415", description = "Szerkezetileg hibás hitelesitő kód megadása", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása requestBody nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @PostMapping("/checkVerificationCode")
    public ResponseEntity<Object> checkVerificationCode(@RequestBody JsonNode requestBody) {
        return userService.checkVerificationCode(requestBody.get("vCode").asText(null), requestBody.get("email").asText(null));
    }

    @Operation(summary = "Jelszó módosítás", description = "Jelszó módosítás")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres jelszó módositás"),
            @ApiResponse(responseCode = "404", description = "A felhasználó által megadott fiók nem létezik", content = @Content),
            @ApiResponse(responseCode = "415", description = "Szerkezetileg helytelen e-mail cím vagy jelszó", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása request body nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server okozta hiba.", content = @Content)
    })
    @PatchMapping("/passwordReset")
    public ResponseEntity<HashMap<String, String>> updatePassword(@RequestBody JsonNode body) {
        HashMap<String, String> returnObject = new HashMap<>();
        returnObject.put("result", userService.updatePassword(body.get("email").asText(), body.get("newPassword").asText()).getBody());
        return ResponseEntity.ok(returnObject);
    }

    @Operation(summary = "Felhasználó id alapján.", description = "Felhasználó visszaszerzése id alapján.")
    @Parameter(name = "id", description = "A felhasználóhoz tartozó id.", in = ParameterIn.PATH, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés."),
            @ApiResponse(responseCode = "404", description = "Nem létező felhasználó törlése", content = @Content),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter nélkül", content = @Content),
            @ApiResponse(responseCode = "500", description = "A server által okozott hiba.", content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }
}