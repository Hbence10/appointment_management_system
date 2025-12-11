package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A felhasználónevet és jelszavat tartalmazza", required = true,
            content = @Content(

    ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres bejelentkezés."),
            @ApiResponse(responseCode = "404", description = "Sikertelen bejelentkezés, téves felhasználónév vagy email cím."),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása requestBody nélkül."),
            @ApiResponse(responseCode = "500", description = "A server által okozott hiba."),
    })
    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody JsonNode loginBody) {
        return userService.login(loginBody.get("username").asText(), loginBody.get("password").asText());
    }

    @Operation(summary = "Regisztráció", description = "Új felhasználó regisztrálása.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, useParameterTypeSchema = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres regisztráció."),
            @ApiResponse(responseCode = "409", description = "Olyan e-mail címmel vagy felhasználónévvel regisztrált a felhasználó, amely már létezik az adatbázisban."),
            @ApiResponse(responseCode = "415", description = "Felépitésben helytelen e-mail cím vagy felhasználónév"),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása request body nélkül."),
            @ApiResponse(responseCode = "500", description = "A server által okozott hiba."),
    })
    @PostMapping("/register")
    public ResponseEntity<Object> registration(@RequestBody Users newUser) {
        return userService.register(newUser);
    }

    @Operation(summary = "Felhasználó frissitése.", description = "Felhasználó frissitése")
    @Parameter(name = "id", description = "A felhasználóhoz tartozó id.", required = true, in = ParameterIn.PATH)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "", required = true, useParameterTypeSchema = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres frissités"),
            @ApiResponse(responseCode = "404", description = "Nem létező felhasználó frissitése"),
            @ApiResponse(responseCode = "409", description = "Már foglalt e-cimre vagy felhasználónévre való frissités"),
            @ApiResponse(responseCode = "415", description = "Felépitésben helytelen email cim."),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása parameter vagy request body nélkül."),
            @ApiResponse(responseCode = "500", description = "A server által okozott hiba."),
    })
    @PatchMapping("/updateUser/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") Long id, @RequestBody JsonNode requestBody) {
        return userService.updateUser(id, requestBody.get("email").asText(), requestBody.get("username").asText());
    }

    @Operation(summary = "Felhasználó törlése", description = "Felhasználó törlése id alapján")
    @Parameter(name = "id", description = "A felhasználóhoz tartozó id.", in = ParameterIn.PATH, required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres törlés"),
            @ApiResponse(responseCode = "404", description = "Nem létező felhasználó törlése"),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghivása parameter nélkül"),
            @ApiResponse(responseCode = "500", description = "A server által okozott hiba."),
    })
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

    //PFP
    @Operation(summary = "", description = "")
    @Parameters({
            @Parameter(name = "", description = "", required = true, in = ParameterIn.PATH),
            @Parameter(name = "", description = "", required = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "", description = ""),
            @ApiResponse(responseCode = "", description = ""),
            @ApiResponse(responseCode = "", description = ""),
            @ApiResponse(responseCode = "", description = ""),
    })
    @PatchMapping("/changePfp/{id}")
    public ResponseEntity<Users> changePfp(@PathVariable("id") Long id, @RequestParam("pfpImg") MultipartFile file) {
        return userService.changePfp(id, file);
    }

    //password-reset
    @Operation(summary = "Hitelesitő kód küldés", description = "A megadott email-re a jelszó frissitéshez szükéseg hitelesitő kód küldése.")
    @Parameter(name = "email", description = "A felhasználó által megadott email a hitelesitő kód küldéséhez.", required = true, in = ParameterIn.QUERY)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sikeres kód küldése."),
            @ApiResponse(responseCode = "404", description = "Olyan email címet adott meg a felhasználó amely nem szerepel az adatbázisban."),
            @ApiResponse(responseCode = "415", description = "Felépitésben helytelen email cimet adott meg a felhasználó."),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter nélkül."),
            @ApiResponse(responseCode = "500", description = "A server által okozott hiba."),
    })
    @GetMapping("/getVerificationCode")
    public ResponseEntity<String> getVerificationCode(@RequestParam("email") String email) {
        return userService.getVerificationCode(email);
    }

    @Operation(summary = "", description = "")
    @Parameters({
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "", description = "", in = ParameterIn.QUERY, required = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "", description = ""),
            @ApiResponse(responseCode = "", description = ""),
            @ApiResponse(responseCode = "", description = ""),
            @ApiResponse(responseCode = "", description = "")
    })
    @PostMapping("/checkVerificationCode")
    public ResponseEntity<Object> checkVerificationCode(@RequestBody JsonNode requestBody) {
        return userService.checkVerificationCode(requestBody.get("vCode").asText(null), requestBody.get("email").asText(null));
    }

    @Operation(summary = "", description = "")
    @Parameters({
            @Parameter(name = "", description = "", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "", description = "", required = true, in = ParameterIn.QUERY),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "", description = ""),
            @ApiResponse(responseCode = "", description = ""),
            @ApiResponse(responseCode = "", description = ""),
            @ApiResponse(responseCode = "", description = ""),
            @ApiResponse(responseCode = "", description = "")
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
            @ApiResponse(responseCode = "404", description = "Nem létező felhasználó törlése"),
            @ApiResponse(responseCode = "422", description = "Az endpoint meghívása parameter nélkül"),
            @ApiResponse(responseCode = "500", description = "A server által okozott hiba."),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    //Error lekezelesek:
    @ExceptionHandler
    public ResponseEntity<String> handleUniqueError(DataIntegrityViolationException e) {
        String errorMsg = "";

        if (e.getMessage().contains("key 'email'")) {
            errorMsg = "duplicateEmail";
        } else {
            errorMsg = "duplicateUsername";
        }

        return ResponseEntity.status(409).body(errorMsg);
    }
}