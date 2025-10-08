package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.configurations.emailSender.EmailSender;
import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import com.Hbence.appointmentManagementAPI.service.other.ValidatorCollection;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;
    private String vCode = "";

    //Endpointok
    public ResponseEntity<Users> login(String username, String password) {
        Users loggedUser = userRepository.login(username);

        boolean successFullLogin = passwordEncoder.matches(password, loggedUser.getPassword());

        if (!successFullLogin || loggedUser.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(loggedUser);
    }

    public ResponseEntity<Object> register(Users newUser) {
        System.out.println(newUser);

        if (!ValidatorCollection.emailChecker(newUser.getEmail()) && !ValidatorCollection.passwordChecker(newUser.getPassword())) {
            return ResponseEntity.status(417).body("InvalidPasswordAndEmail");
        } else if (!ValidatorCollection.emailChecker(newUser.getEmail())) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else if (!ValidatorCollection.passwordChecker(newUser.getPassword())) {
            return ResponseEntity.status(417).body("InvalidPassword");
        } else {
            String hashedPassword = passwordEncoder.encode(newUser.getPassword());
            newUser.setPassword(hashedPassword);
            Users registeredUser = userRepository.save(newUser);
            return ResponseEntity.ok(registeredUser);
        }
    }

    @PreAuthorize("hasRole('user', 'admin' 'superAdmin')")
    public ResponseEntity<String> deleteUser(Long id) {
        Users searchedUser = userRepository.findById(id).orElse(null);
        if (searchedUser == null) {
            return ResponseEntity.notFound().build();
        }

        searchedUser.setIsDeleted(true);

        searchedUser.setDeletedAt(new Date());
        userRepository.save(searchedUser);
        return ResponseEntity.ok("successfullyDelete");
    }

    @PreAuthorize("hasRole('user', 'admin' 'superAdmin')")
    public ResponseEntity<Object> updateUser(Users updatedUser) {
        if (updatedUser.getId() == null) {
            return ResponseEntity.notFound().build();
        } else if (!ValidatorCollection.emailChecker(updatedUser.getEmail())) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else {
            return ResponseEntity.ok(userRepository.save(updatedUser));
        }

    }

    public ResponseEntity<String> updatePassword(String email, String newPassword, String userVCode) {
        Users user = userRepository.getUserByEmail(email);

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
            user.setPassword(hashedPassword);
            userRepository.save(user);
            return ResponseEntity.ok("successfullyReset");
        }
    }

    public ResponseEntity<String> getVerificationCode(String email) {
        List<String> emailList = userRepository.getAllEmail();

        if (!ValidatorCollection.emailChecker(email.trim())) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else if (!emailList.contains(email.trim())) {
            return ResponseEntity.notFound().build();
        } else {
            this.vCode = ValidatorCollection.generateVerificationCode();
            emailSender.sendVerificationCodeEmail(email, vCode);
            return ResponseEntity.ok("success");
        }
    }

    public ResponseEntity<Object> checkVCode(String userVCode) {
        if (userVCode.length() != 10) {
            return ResponseEntity.status(417).body("InvalidVerificationCode");
        } else {
            if (userVCode.equals(this.vCode)) {
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.ok(false);
            }
        }
    }
}
