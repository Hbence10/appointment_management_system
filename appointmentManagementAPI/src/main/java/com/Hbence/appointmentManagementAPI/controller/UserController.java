package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody Map<String, String> loginBody) {
        return userService.login(loginBody.get("username"), loginBody.get("password"));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registration(@RequestBody Users newUser) {
        return userService.register(newUser);
    }

    @PatchMapping("/updateUser/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") Long id, @RequestBody Map<String, String> requestBody) {
        return userService.updateUser(id, requestBody.get("email"), requestBody.get("username"));
    }

    @PatchMapping("/changePfp/{id}")
    public ResponseEntity<Object> changePfp(@PathVariable("id") Long id){
        return userService.changePfp(id);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

    //password-reset
    @GetMapping("/getVerificationCode")
    public ResponseEntity<String> getVerificationCode(@RequestParam("email") String email) {
        return userService.getVerificationCode(email);
    }

    @PostMapping("/checkVerificationCode")
    public ResponseEntity<Object> checkVerificationCode(@RequestBody Map<String, String> codeObject) {
        return userService.checkVCode(codeObject.get("vCode"));
    }

    @PatchMapping("/passwordReset")
    public ResponseEntity<HashMap<String, String>> updatePassword(@RequestBody Map<String, String> body) {
        HashMap<String, String> returnObject = new HashMap<>();
        returnObject.put("result", userService.updatePassword(body.get("email"), body.get("newPassword"), body.get("vCode")).getBody());
        return ResponseEntity.ok(returnObject);
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