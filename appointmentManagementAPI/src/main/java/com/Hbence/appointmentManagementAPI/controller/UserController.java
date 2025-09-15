package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.User;
import com.Hbence.appointmentManagementAPI.other.Response;
import com.Hbence.appointmentManagementAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public Response login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.login(username, password);
    }

    @PostMapping("/register")
    public Response registration(@RequestBody User newUser) {
        return userService.register(newUser);
    }
}