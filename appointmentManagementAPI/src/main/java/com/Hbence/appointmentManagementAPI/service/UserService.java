package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String login(String username, String password){
        /*
        *  Validaciok:
        *           helytelen jelszo
        *           helytelen username
        *           helytelen jelszo & username
        * */

        return "";
    }
}
