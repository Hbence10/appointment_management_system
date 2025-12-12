package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.configurations.emailSender.EmailSender;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

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
        try {
            if (username == null || password == null) {
                return ResponseEntity.status(422).build();
            }

            Users loggedUser = userRepository.login(username);

            boolean successFullLogin = passwordEncoder.matches(password, loggedUser.getPassword());

            if (!successFullLogin || loggedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }

            loggedUser.setLastLogin(new Date());
            userRepository.save(loggedUser);

            return ResponseEntity.ok(loggedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> register(Users newUser) {
        try {
            if (newUser == null) {
                return ResponseEntity.status(422).build();
            }

            if (!ValidatorCollection.emailChecker(newUser.getEmail()) && !ValidatorCollection.passwordChecker(newUser.getPassword())) {
                return ResponseEntity.status(415).body("InvalidPasswordAndEmail");
            } else if (!ValidatorCollection.emailChecker(newUser.getEmail())) {
                return ResponseEntity.status(415).body("InvalidEmail");
            } else if (!ValidatorCollection.passwordChecker(newUser.getPassword())) {
                return ResponseEntity.status(415).body("InvalidPassword");
            } else {
                String hashedPassword = passwordEncoder.encode(newUser.getPassword());
                newUser.setPassword(hashedPassword);
                Users registeredUser = userRepository.save(newUser);
                emailSender.sendEmailAboutRegistration(newUser.getEmail());
                return ResponseEntity.ok(registeredUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<Object> updateUser(Long id, String email, String username) {
        try {
            if (id == null || email == null || username == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.findById(id).get();
            if (searchedUser.getId() == null) {
                return ResponseEntity.notFound().build();
            } else {
                System.out.println(email);
                if (!ValidatorCollection.emailChecker(email)) {
                    return ResponseEntity.status(415).body("InvalidEmail");
                } else {
                    searchedUser.setUsername(username);
                    searchedUser.setEmail(email);
                    return ResponseEntity.ok(userRepository.save(searchedUser));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<String> deleteUser(Long id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

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
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<Users> changePfp(Long userId, MultipartFile pfpFile) {
        try {
            if (userId == null || pfpFile == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.findById(userId).get();

            if (searchedUser.getId() == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                String filePath = "C:\\Users\\bzhal\\Documents\\GitHub\\appointment_management_system\\pmsWebPage\\src\\assets\\images\\pfp" + File.separator + pfpFile.getOriginalFilename();

                try {
                    FileOutputStream fout = new FileOutputStream(filePath);
                    fout.write(pfpFile.getBytes());
                    fout.close();

                    searchedUser.setPfpPath("assets\\images\\pfp" + File.separator + pfpFile.getOriginalFilename());
                } catch (Exception e) {
                    return ResponseEntity.internalServerError().build();
                }

                return ResponseEntity.ok().body(userRepository.save(searchedUser));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //Password-reset:
    public ResponseEntity<String> getVerificationCode(String email) {
        try {
            if (email == null) {
                return ResponseEntity.status(422).build();
            }

            List<String> emailList = userRepository.getAllEmail();

            if (!ValidatorCollection.emailChecker(email.trim())) {
                return ResponseEntity.status(415).body("InvalidEmail");
            } else if (!emailList.contains(email.trim())) {
                return ResponseEntity.notFound().build();
            } else {
                this.vCode = ValidatorCollection.generateVerificationCode();
                emailSender.sendVerificationCodeEmail(email, vCode);
                return ResponseEntity.ok("success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> checkVerificationCode(String userVCode, String email) {
        try {
            if (userVCode == null || email == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.getUserByEmail(email);

            if (userVCode.length() != 10) {
                return ResponseEntity.status(415).body("InvalidVerificationCode");
            } else {
                if (userVCode.equals(this.vCode)) {
                    return ResponseEntity.ok(true);
                } else {
                    return ResponseEntity.ok(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<String> updatePassword(String email, String newPassword) {
        try {
            if (email == null || newPassword == null) {
                return ResponseEntity.status(422).build();
            }

            Users user = userRepository.getUserByEmail(email);

            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            if (!ValidatorCollection.emailChecker(email) && !ValidatorCollection.passwordChecker(newPassword)) {
                return ResponseEntity.status(415).body("InvalidPasswordAndEmail");
            } else if (!ValidatorCollection.emailChecker(email)) {
                return ResponseEntity.status(415).body("InvalidEmail");
            } else if (!ValidatorCollection.passwordChecker(newPassword)) {
                return ResponseEntity.status(415).body("InvalidPassword");
            } else if (user == null) {
                return ResponseEntity.notFound().build();
            } else {
                String hashedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(hashedPassword);
                userRepository.save(user);
                return ResponseEntity.ok("successfullyReset");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Users> getUserById(Long id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.findById(id).get();
            if (searchedUser == null || searchedUser.getId() == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(searchedUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /*
     * HTTP STATUS KODOK:
     *   - 200: Sikeres muvelet
     *   - 404: Not Found
     *   - 409: Mar foglalt nev
     *   - 415: Unsupported Media Type --> Ha az adott adat invalid
     *   - 422: Hianyzo parameter/response body
     *   - 500: Internal Server Error
     * */
}
