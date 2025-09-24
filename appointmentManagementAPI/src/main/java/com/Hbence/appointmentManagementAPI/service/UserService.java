package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import com.Hbence.appointmentManagementAPI.service.other.ValidatorCollection;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    //Endpointok
    public ResponseEntity<Users> login(String username, String password) {
        Users loggedUsers = userRepository.login(username);

        boolean successFullLogin = passwordEncoder.matches(password, loggedUsers.getPassword());

        if (!successFullLogin || loggedUsers.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(loggedUsers);
    }

    public ResponseEntity<Object> register(Users newUsers) {
        if (ValidatorCollection.emailChecker(newUsers.getEmail()) && ValidatorCollection.passwordChecker(newUsers.getPassword())) {
            String hashedPassword = passwordEncoder.encode(newUsers.getPassword());
            newUsers.setPassword(hashedPassword);
            Users registeredUsers = userRepository.save(newUsers);
            return ResponseEntity.ok(registeredUsers);
        } else if (!ValidatorCollection.emailChecker(newUsers.getEmail()) && !ValidatorCollection.passwordChecker(newUsers.getPassword())) {
            return ResponseEntity.status(417).body("InvalidPasswordAndEmail");
        } else if (!ValidatorCollection.emailChecker(newUsers.getEmail())) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else if (!ValidatorCollection.passwordChecker(newUsers.getPassword())) {
            return ResponseEntity.status(417).body("InvalidPassword");
        }

        return ResponseEntity.internalServerError().build();
    }

    public ResponseEntity<Users> updateUser(Users updatedUsers) {
        return null;
    }

    public ResponseEntity<Users> updatePassword(Long id, Map<String, String> newPasswordBody) {
        return null;
    }

    public ResponseEntity<String> deleteUser(Long id) {
        return null;
    }
}
