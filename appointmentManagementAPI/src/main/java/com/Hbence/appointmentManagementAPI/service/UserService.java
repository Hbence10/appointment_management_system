package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.configurations.emailSender.EmailSender;
import com.Hbence.appointmentManagementAPI.entity.AdminDetails;
import com.Hbence.appointmentManagementAPI.entity.Role;
import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.repository.AdminDetailsRepository;
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
    private final AdminDetailsRepository adminDetailsRepository;
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

        loggedUser.setLastLogin(new Date());
        userRepository.save(loggedUser);

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

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<String> deleteUser(Long id) {
        Users searchedUser = userRepository.findById(id).orElse(null);
        if (searchedUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            searchedUser.setIsDeleted(true);
            searchedUser.setDeletedAt(new Date());

            if (searchedUser.getAdminDetails() != null) {
                searchedUser.getAdminDetails().setDeletedAt(new Date());
                searchedUser.getAdminDetails().setIsDeleted(true);
            }

            userRepository.save(searchedUser);
            emailSender.sendEmailAboutUserDelete(searchedUser.getEmail());
            return ResponseEntity.ok().build();
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<Object> updateUser(Long id, String email, String username) {
        Users searchedUser = userRepository.findById(id).get();
        if (searchedUser.getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            System.out.println(email);
            if (!ValidatorCollection.emailChecker(email)) {
                return ResponseEntity.status(409).body("InvalidEmail");
            } else {
                searchedUser.setUsername(username);
                searchedUser.setEmail(email);
                return ResponseEntity.ok(userRepository.save(searchedUser));
            }
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<Object> changePfp(Long id) {
        return null;
    }

    //Adminok kezelese
    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Users> makeAdmin(Long id, AdminDetails details) {
        Users searchedUser = userRepository.findById(id).get();

        if (searchedUser.getId() == null || searchedUser.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else if (!ValidatorCollection.emailChecker(details.getEmail())) {
            return ResponseEntity.status(407).build();
        } else if (details.getId() != null) {
            return ResponseEntity.internalServerError().build();
        } else {
            searchedUser.setRole(new Role(Long.valueOf("2"), "ROLE_admin"));
            adminDetailsRepository.save(details);
            searchedUser.setAdminDetails(details);
            return ResponseEntity.ok(userRepository.save(searchedUser));
        }
    }

    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<List<Users>> getAllAdmin() {
        return ResponseEntity.ok().body(userRepository.getAllAdmin());
    }

    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Object> updateAdmin(AdminDetails updatedAdminDetails) {
        AdminDetails testDetails = adminDetailsRepository.findById(updatedAdminDetails.getId()).get();

        if (testDetails.getId() == null || testDetails.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else if (!ValidatorCollection.emailChecker(updatedAdminDetails.getEmail())) {
            return ResponseEntity.status(407).build();
        } else {
            return ResponseEntity.ok().body(adminDetailsRepository.save(updatedAdminDetails));
        }

    }

    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Object> deleteAdmin(Long id) {
        AdminDetails searchedAdminDetails = adminDetailsRepository.findById(id).get();
        if (searchedAdminDetails.getId() == null || searchedAdminDetails.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            searchedAdminDetails.setIsDeleted(true);
            searchedAdminDetails.setDeletedAt(new Date());
            adminDetailsRepository.save(searchedAdminDetails);
            return ResponseEntity.ok().build();
        }
    }

    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Object> getShortUsersList(){
        List<Users> userList = userRepository.findAll().stream().filter(user -> user.getRole().getId() == 1 && !user.getIsDeleted()).toList();
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (int i = 0; i < userList.size(); i++){
            Map<String, Object> eachResponse = new HashMap<>();
            eachResponse.put("id", userList.get(i).getId());
            eachResponse.put("username", userList.get(i).getUsername());
            responseList.add(eachResponse);
        }

        return ResponseEntity.ok().body(responseList);
    }

    //Password-reset:
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
