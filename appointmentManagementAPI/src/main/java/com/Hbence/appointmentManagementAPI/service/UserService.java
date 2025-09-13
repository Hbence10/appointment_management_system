package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.User;
import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        System.out.println(userRepository.login(username, password).getUsername());
        return userRepository.login(username, password);
    }

    public Response register(User newUser) {
        String result = userRepository.register(newUser.getUsername(), newUser.getEmail(), newUser.getPassword());
        return new Response(HttpStatus.OK.value(), result, LocalDateTime.now());
    }
}
