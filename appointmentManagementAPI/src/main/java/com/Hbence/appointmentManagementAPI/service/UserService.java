package com.Hbence.appointmentManagementAPI.service;

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
    private static Boolean passwordValidator(){

        return false;
    }


    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response login(String username, String password){

        try {
            int id = userRepository.asd(username, password);
            return new Response(HttpStatus.OK.value(), userRepository.findById(id).get(), LocalDateTime.now());
        } catch (NullPointerException e){
            throw new UserNotFound("InvalidUsernameOrPassword");
        }




    }
}
