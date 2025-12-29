package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.configurations.emailSender.EmailSender;
import com.Hbence.appointmentManagementAPI.configurations.security.JWTToken.RefresherToken.RefreshToken;
import com.Hbence.appointmentManagementAPI.configurations.security.JWTToken.RefresherToken.RefreshTokenRepository;
import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.repository.AdminDetailsRepository;
import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import com.Hbence.appointmentManagementAPI.service.other.ValidatorCollection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;

@Service
@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;

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

            if (newUser.getId() != null) {
                return ResponseEntity.status(415).body("invalidObject");
            } else if (!ValidatorCollection.emailChecker(newUser.getEmail())) {
                return ResponseEntity.status(415).body("invalidEmail");
            } else if (!ValidatorCollection.passwordChecker(newUser.getPassword())) {
                return ResponseEntity.status(415).body("invalidPassword");
            } else {
                String hashedPassword = passwordEncoder.encode(newUser.getPassword());
                newUser.setPassword(hashedPassword);
                Users registeredUser = userRepository.save(newUser);
                try {
                    emailSender.sendEmailAboutRegistration(newUser.getEmail());
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.internalServerError().body("emailSenderError");
                }
                return ResponseEntity.ok(registeredUser);
            }
        } catch (DataIntegrityViolationException e) {
            String errorMsg = e.getMessage().contains("Duplicate entry") && e.getMessage().contains("for key 'email'") ? "emailDuplicate" : "usernameDuplicate";
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.status(409).body(errorMsg);
        } catch (RuntimeException e) {
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

            Users searchedUser = userRepository.findById(id).orElse(null);
            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                if (!ValidatorCollection.emailChecker(email)) {
                    return ResponseEntity.status(415).body("InvalidEmail");
                } else {
                    searchedUser.setUsername(username);
                    searchedUser.setEmail(email);
                    return ResponseEntity.ok(userRepository.save(searchedUser));
                }
            }
        } catch (DataIntegrityViolationException e) {
            String errorMsg = e.getMessage().contains("Duplicate entry") && e.getMessage().contains("for key 'email'") ? "emailDuplicate" : "usernameDuplicate";
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.status(409).body(errorMsg);
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
            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                searchedUser.setIsDeleted(true);
                searchedUser.setDeletedAt(new Date());

                if (searchedUser.getAdminDetails() != null) {
                    searchedUser.getAdminDetails().setDeletedAt(new Date());
                    searchedUser.getAdminDetails().setIsDeleted(true);
                }

                userRepository.save(searchedUser);
                try {
                    emailSender.sendEmailAboutUserDelete(searchedUser.getEmail());
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.internalServerError().body("emailSenderError");
                }
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<Object> changePfp(Long userId, MultipartFile pfpFile) {
        try {
            if (userId == null || pfpFile == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.findById(userId).orElse(null);

            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                String filePath = "C:\\Users\\bzhal\\Documents\\GitHub\\appointment_management_system\\pmsWebPage\\src\\assets\\images\\pfp" + File.separator + pfpFile.getOriginalFilename();

                try {
                    FileOutputStream fout = new FileOutputStream(filePath);
                    fout.write(pfpFile.getBytes());
                    fout.close();

                    searchedUser.setPfpPath("assets\\images\\pfp" + File.separator + pfpFile.getOriginalFilename());
                } catch (Exception e) {
                    return ResponseEntity.internalServerError().body("fileUploadError");
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

            Users searchedUser = userRepository.getUserByEmail(email).orElse(null);

            if (!ValidatorCollection.emailChecker(email.trim())) {
                return ResponseEntity.status(415).body("InvalidEmail");
            } else if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                String vCode = ValidatorCollection.generateVerificationCode();
                searchedUser.setVCode(passwordEncoder.encode(vCode));
                userRepository.save(searchedUser);
                try {
                    emailSender.sendVerificationCodeForPasswordResetEmail(email, vCode);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.internalServerError().body("emailSenderError");
                }
                return ResponseEntity.ok().build();
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

            Users searchedUser = userRepository.getUserByEmail(email).orElse(null);
            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }

            if (userVCode.length() != 10) {
                return ResponseEntity.status(415).body("InvalidVerificationCode");
            } else {
                JsonNode returnObject = objectMapper.createObjectNode();
                ((ObjectNode) returnObject).put("success", passwordEncoder.matches(userVCode, searchedUser.getVCode()));
                return ResponseEntity.ok().body(returnObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> updatePassword(String email, String newPassword) {
        try {
            if (email == null || newPassword == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.getUserByEmail(email).orElse(null);

            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }

            if (!ValidatorCollection.emailChecker(email) && !ValidatorCollection.passwordChecker(newPassword)) {
                return ResponseEntity.status(415).body("InvalidPasswordAndEmail");
            } else if (!ValidatorCollection.emailChecker(email)) {
                return ResponseEntity.status(415).body("InvalidEmail");
            } else if (!ValidatorCollection.passwordChecker(newPassword)) {
                return ResponseEntity.status(415).body("InvalidPassword");
            } else {
                String hashedPassword = passwordEncoder.encode(newPassword);
                searchedUser.setPassword(hashedPassword);
                userRepository.save(searchedUser);
                return ResponseEntity.ok().build();
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

            Users searchedUser = userRepository.findById(id).orElse(null);
            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(searchedUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('user', 'admin', 'superAdmin')")
    public ResponseEntity<Object> logout(Long id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.findById(id).orElse(null);
            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            }
            RefreshToken searchedToken = refreshTokenRepository.getRefreshTokenByUserId(id).orElse(null);
            if (searchedToken == null){
                return ResponseEntity.notFound().build();
            } else {
                refreshTokenRepository.delete(searchedToken);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
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
