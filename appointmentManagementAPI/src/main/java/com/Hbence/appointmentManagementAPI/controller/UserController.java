package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.User;
import com.Hbence.appointmentManagementAPI.service.UserService;
import com.Hbence.appointmentManagementAPI.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public User login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.login(username, password);
    }

    @PostMapping("/register")
    public Response registration(@RequestBody User newUser) {
        return userService.register(newUser);
    }
}

/*
 * a kovetkezo endpointok fognak kelleni:
 *           bejelentkezes
 *           regisztracio
 *           kijelentkezes
 *           adatok frissitese
 *           jelszo frissitese
 *           torles
 * */