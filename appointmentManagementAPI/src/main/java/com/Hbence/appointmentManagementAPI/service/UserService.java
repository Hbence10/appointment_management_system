package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.configurations.emailSender.EmailSender;
import com.Hbence.appointmentManagementAPI.entity.User;
import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import com.Hbence.appointmentManagementAPI.service.other.ValidatorCollection;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;

    //Endpointok
    public ResponseEntity<User> login(String username, String password) {
        User loggedUser = userRepository.login(username);

        boolean successFullLogin = passwordEncoder.matches(password, loggedUser.getPassword());

        if (!successFullLogin || loggedUser.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(loggedUser);
    }

    public ResponseEntity<Object> register(User newUser) {
        if (ValidatorCollection.emailChecker(newUser.getEmail()) && ValidatorCollection.passwordChecker(newUser.getPassword())) {
            String hashedPassword = passwordEncoder.encode(newUser.getPassword());
            newUser.setPassword(hashedPassword);
            User registeredUser = userRepository.save(newUser);
            return ResponseEntity.ok(registeredUser);
        } else if (!ValidatorCollection.emailChecker(newUser.getEmail()) && !ValidatorCollection.passwordChecker(newUser.getPassword())) {
            return ResponseEntity.status(417).body("InvalidPasswordAndEmail");
        } else if (!ValidatorCollection.emailChecker(newUser.getEmail())) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else if (!ValidatorCollection.passwordChecker(newUser.getPassword())) {
            return ResponseEntity.status(417).body("InvalidPassword");
        }

        return ResponseEntity.internalServerError().build();
    }

    public ResponseEntity<String> updatePassword(String email, String newPassword) {
        User user = userRepository.getUserByEmail(email);

        if (!ValidatorCollection.emailChecker(email) && !ValidatorCollection.passwordChecker(newPassword)) {
            return ResponseEntity.status(417).body("InvalidPasswordAndEmail");
        } else if (!ValidatorCollection.emailChecker(email)) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else if (!ValidatorCollection.passwordChecker(newPassword)) {
            return ResponseEntity.status(417).body("InvalidPassword");
        } else if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            String hashedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(newPassword);
            userRepository.save(user);
            return ResponseEntity.ok("successfullyReset");
        }
    }

    public ResponseEntity<String> getVerificationCode(String email){
//        emailSender.
        return null;
    }

    public ResponseEntity<String> deleteUser(Long id) {
        return null;
    }

    public ResponseEntity<User> updateUser(User updatedUser) {
        return null;
    }

    //egyeb:
    public static String generateVerificationCode() {
        String code = "";
        String specialCharacters = "\"!@#$%^&*()-_=+[]{};:,.?/\"";
        ArrayList<String> characters = new ArrayList<String>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));

        for (int i = 97; i <= 122; i++) {
            characters.add(String.valueOf((char) i));
        }

        for (int i = 0; i < specialCharacters.length(); i++) {
            characters.add(String.valueOf(specialCharacters.charAt(i)));
        }

        while(code.length() != 10){
            Random random = new Random();
            code += characters.get(random.nextInt(characters.size()));
        }

        return code;
    }

}
