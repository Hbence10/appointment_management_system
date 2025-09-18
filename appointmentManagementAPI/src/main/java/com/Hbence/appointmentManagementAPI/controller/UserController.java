package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.User;
import com.Hbence.appointmentManagementAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.login(username, password);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registration(@RequestBody User newUser) {
        return userService.register(newUser);
    }

    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {
        return userService.updateUser(updatedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable("id") Long id, @RequestBody Map<String, String> newPasswordBody) {
        return userService.updatePassword(id, newPasswordBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

    //Error lekezelesek:
    @ExceptionHandler
    public ResponseEntity<User> handleUniqueError(DataIntegrityViolationException e) {
        return ResponseEntity.notFound().build();
    }
}