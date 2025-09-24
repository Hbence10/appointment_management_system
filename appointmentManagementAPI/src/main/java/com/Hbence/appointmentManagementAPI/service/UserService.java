package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.User;
import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import com.Hbence.appointmentManagementAPI.service.other.ValidatorCollection;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Pattern;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

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

    public ResponseEntity<User> updateUser(User updatedUser) {
        return null;
    }

    public ResponseEntity<User> updatePassword(Long id, Map<String, String> newPasswordBody) {
        return null;
    }

    public ResponseEntity<String> deleteUser(Long id) {
        return null;
    }
}
