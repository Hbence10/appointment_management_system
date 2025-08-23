package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.dao.UserRepository;
import com.Hbence.appointmentManagementAPI.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password){
        /*
        *  Validaciok:
        *           helytelen jelszo
        *           helytelen username
        *           helytelen jelszo & username
        * */

        int id = userRepository.asd(username, password);
        return userRepository.findById(id).get();
//        retu/rn null;
    }
}
