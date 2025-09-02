package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.User;
import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import com.Hbence.appointmentManagementAPI.service.exceptions.ExceptionType.UserNotFound;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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
        return userRepository.login(username, password);
    }

    public Response register(User newUser) {
        Response response = new Response();

        if (passwordValidator(newUser.getPassword()) && emailValidator(newUser.getEmail())) {
            String result = userRepository.register(newUser.getUsername(), newUser.getEmail(), newUser.getPassword());

            if(result.equals("successfull registration")){
                response = new Response(HttpStatus.OK.value(), result, LocalDateTime.now());
            }
        } else {

        }

        return response;
    }
}
