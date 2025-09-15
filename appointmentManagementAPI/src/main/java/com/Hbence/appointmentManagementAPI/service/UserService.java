package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.User;
import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import com.Hbence.appointmentManagementAPI.service.exceptions.ExceptionType.InvalidEmail;
import com.Hbence.appointmentManagementAPI.service.exceptions.ExceptionType.InvalidEmailAndPassword;
import com.Hbence.appointmentManagementAPI.service.exceptions.ExceptionType.InvalidPassword;
import com.Hbence.appointmentManagementAPI.service.exceptions.ExceptionType.UserNotFound;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final String SPECIAL = "!@#$%^&*()-_=+[]{};:,.?/";

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        User loggedUser = userRepository.login(username, password);

        if (loggedUser == null) {
            throw new UserNotFound("User not Found");
        }

        return loggedUser;
    }

    public Response register(User newUser) {
        String result = "";

        if (emailChecker(newUser.getEmail()) && passwordChecker(newUser.getPassword())) {
            result = userRepository.register(newUser.getUsername(), newUser.getEmail(), newUser.getPassword());

        } else if (!emailChecker(newUser.getEmail()) && !passwordChecker(newUser.getPassword())) {
            throw new InvalidEmailAndPassword("Invalid email & password");

        } else if (!emailChecker(newUser.getEmail())) {
            throw new InvalidEmail("Invalid email");

        } else if (!passwordChecker(newUser.getPassword())) {
            throw new InvalidPassword("Invalid Password");
        }

        return new Response(HttpStatus.OK.value(), result, LocalDateTime.now());
    }

    //---------------------------------
    //Egyeb
    public static boolean emailChecker(String email) {
        if (email == null || email.length() > 100) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private static boolean passwordChecker(String password) {
        if (password.length() < 8 || password.length() > 16) {
            return false;
        }

        String specialCharacters = "\"!@#$%^&*()-_=+[]{};:,.?/\"";
        String numbersText = "1234567890";
        Boolean specialChecker = false;
        Boolean upperCaseChecker = false;
        Boolean lowerCaseChecker = false;
        Boolean initChecker = false;

        for (int i = 0; i < password.length(); i++) {
            String selectedChar = String.valueOf(password.charAt(i));

            if (numbersText.indexOf(selectedChar) >= 0) {
                initChecker = true;
            } else if (specialCharacters.indexOf(selectedChar) >= 0) {
                specialChecker = true;
            } else if (selectedChar.equals(selectedChar.toUpperCase())) {
                upperCaseChecker = true;
            } else if (selectedChar.equals(selectedChar.toLowerCase())) {
                lowerCaseChecker = true;
            }
        }

        return specialChecker && upperCaseChecker && lowerCaseChecker && initChecker;
    }
}
