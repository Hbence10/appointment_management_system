package com.Hbence.appointmentManagementAPI.controller;

import com.Hbence.appointmentManagementAPI.entity.User;
import com.Hbence.appointmentManagementAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

    @GetMapping("/login")
    public User login(@RequestParam("username") String username, @RequestParam("password") String password){
        return userService.login(username, password);
    }
}
