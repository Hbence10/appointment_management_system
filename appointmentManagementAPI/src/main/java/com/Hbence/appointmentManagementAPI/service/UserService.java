package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.User;
import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import com.Hbence.appointmentManagementAPI.service.exceptions.ExceptionType.UserNotFound;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Transactional
@Service
public class UserService {
    private static Boolean passwordValidator(String password) {

        return true;
    }

    private static Boolean emailValidator(String email) {

        return true;
    }

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        User user = userRepository.login(username, password);
        return user;
    }

    public Response register(User newUser) {
        if (passwordValidator(newUser.getPassword()) && emailValidator(newUser.getEmail())) {
            userRepository.register(newUser.getUsername(), newUser.getEmail(), newUser.getPassword());
        }

        return new Response(HttpStatus.OK.value(), "succeess", LocalDateTime.now());
    }
}
