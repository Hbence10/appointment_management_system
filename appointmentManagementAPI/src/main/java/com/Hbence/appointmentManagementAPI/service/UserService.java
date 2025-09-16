package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.User;
import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
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

    //Endpointok
    public ResponseEntity<User> login(String username, String password) {
        User loggedUser = userRepository.login(username, password);

        if (loggedUser == null || loggedUser.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(loggedUser);
    }

    public ResponseEntity<Object> register(User newUser) {
        if (emailChecker(newUser.getEmail()) && passwordChecker(newUser.getPassword())) {
            User registeredUser = userRepository.save(newUser);
            return ResponseEntity.ok(registeredUser);
        } else if (!emailChecker(newUser.getEmail()) && !passwordChecker(newUser.getPassword())) {
            return ResponseEntity.status(417).body("InvalidPasswordAndEmail");
        } else if (!emailChecker(newUser.getEmail())) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else if (!passwordChecker(newUser.getPassword())) {
            return ResponseEntity.status(417).body("InvalidPassword");
        }

        return ResponseEntity.internalServerError().build();
    }

    public ResponseEntity<User> updateUser(User updatedUser){
        return null;
    }

    public ResponseEntity<User> updatePassword(Long id, Map<String, String> newPasswordBody){
        return null;
    }

    public ResponseEntity<String> deleteUser(Long id){
        return null;
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
        boolean specialChecker = false;
        boolean upperCaseChecker = false;
        boolean lowerCaseChecker = false;
        boolean initChecker = false;

        for (int i = 0; i < password.trim().length(); i++) {
            String selectedChar = String.valueOf(password.charAt(i));

            if (numbersText.contains(selectedChar)) {
                initChecker = true;
            } else if (specialCharacters.contains(selectedChar)) {
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
