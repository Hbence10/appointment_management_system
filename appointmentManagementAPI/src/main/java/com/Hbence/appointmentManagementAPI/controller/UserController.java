package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody JsonNode loginBody) {
        return userService.login(loginBody.get("username").asText(), loginBody.get("password").asText());
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registration(@RequestBody Users newUser) {
        return userService.register(newUser);
    }

    @PatchMapping("/updateUser/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") Long id, @RequestBody JsonNode requestBody) {
        return userService.updateUser(id, requestBody.get("email").asText(), requestBody.get("username").asText());
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

    //PFP
    @PatchMapping("/changePfp/{id}")
    public ResponseEntity<Users> changePfp(@PathVariable("id") Long id, @RequestParam("pfpImg") MultipartFile file) {
        return userService.changePfp(id, file);
    }

    //Adminok kezelese
    @GetMapping("")
    public ResponseEntity<Object> getShortUsersList(){
        return userService.getShortUsersList();
    }

    //password-reset
    @GetMapping("/getVerificationCode")
    public ResponseEntity<String> getVerificationCode(@RequestParam("email") String email) {
        return userService.getVerificationCode(email);
    }

    @PostMapping("/checkVerificationCode")
    public ResponseEntity<Object> checkVerificationCode(@RequestBody JsonNode requestBody) {
        return userService.checkVerificationCode(requestBody.get("vCode").asText(null), requestBody.get("email").asText(null));
    }

    @PatchMapping("/passwordReset")
    public ResponseEntity<HashMap<String, String>> updatePassword(@RequestBody JsonNode body) {
        HashMap<String, String> returnObject = new HashMap<>();
        returnObject.put("result", userService.updatePassword(body.get("email").asText(), body.get("newPassword").asText()).getBody());
        return ResponseEntity.ok(returnObject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable("id") Long id){
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