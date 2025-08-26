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

    public Response login(String username, String password) {
        try {
            int id = userRepository.login(username, password);
            return new Response(HttpStatus.OK.value(), userRepository.findById(id).get(), LocalDateTime.now());
        } catch (NullPointerException e) {
            throw new UserNotFound("InvalidUsernameOrPassword");
        }
    }

    public Response register(User newUser) {
        if (passwordValidator(newUser.getPassword()) && emailValidator(newUser.getEmail())) {
            userRepository.register(newUser.getUsername(), newUser.getEmail(), newUser.getPassword());
        }

        return new Response(HttpStatus.OK.value(), "succeess", LocalDateTime.now());
    }
}
