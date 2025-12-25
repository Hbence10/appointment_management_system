package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.configurations.emailSender.EmailSender;
import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.repository.*;
import com.Hbence.appointmentManagementAPI.service.other.ValidatorCollection;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Transactional(noRollbackFor = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLIntegrityConstraintViolationException.class, SQLException.class})
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservedDateRepository reservedDateRepository;
    private final ReservedHoursRepository reservedHoursRepository;
    private final StatusRepository statusRepository;
    private final PhoneCountryCodeRepository phoneCountryCodeRepository;
    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<List<Reservations>> getReservationByUserId(Long userId) {
        try {
            if (userId == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.findById(userId).orElse(null);
            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(searchedUser.getReservations());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> getReservationBetweenIntervallum(String startDateText, String endDateText) {
        try {
            if (startDateText == null || endDateText == null) {
                return ResponseEntity.status(422).build();
            }

            if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
                return ResponseEntity.status(415).build();
            }

            List<ReservedDates> reservedDatesList = reservedDateRepository.reservedDatesByDate(LocalDate.parse(startDateText), LocalDate.parse(endDateText));

            return ResponseEntity.ok(reservedDatesList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<ReservedHours>> getReservedHoursByDay(String wantedDayDate) {
        try {
            if (wantedDayDate == null) {
                return ResponseEntity.status(422).build();
            }

            List<ReservedHours> reservedHoursList = reservedHoursRepository.findAllById(reservedHoursRepository.getReservationByMonth(LocalDate.parse(wantedDayDate)));
            return ResponseEntity.ok(reservedHoursList);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<Reservations>> getReservationByDate(String wantedDate) {
        try {
            if (wantedDate == null) {
                return ResponseEntity.status(422).build();
            }

            List<Long> idList = reservationRepository.getReservationByDate(LocalDate.parse(wantedDate));
            List<Reservations> reservationsList = reservationRepository.findAllById(idList);

            return ResponseEntity.ok(reservationsList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Egyszeru foglalas
    public ResponseEntity<Object> makeReservation(Reservations newReservation) {
        try {
            if (newReservation == null) {
                return ResponseEntity.status(422).build();
            }

            String vCode = "";
            if (newReservation.getId() != null) {
                return ResponseEntity.status(415).body("invalidObject");
            } else if (!ValidatorCollection.emailChecker(newReservation.getEmail().trim())) {
                return ResponseEntity.status(415).body("invalidEmail");
            } else if (!ValidatorCollection.phoneValidator(newReservation.getPhone().trim().replaceAll(" ", ""))) {
                return ResponseEntity.status(415).body("invalidPhoneNumber");
            }

            List<PhoneCountryCode> phoneCountryCodes = phoneCountryCodeRepository.findAll();
            if (phoneCountryCodes.stream().filter(code -> code.getId() == newReservation.getPhoneCountryCode().getId()).toList().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("phoneCountryCodeNotFound");
            }

            if (newReservation.getUser() != null) {
                Users searchedUser = userRepository.findById(newReservation.getUser().getId()).orElse(null);
                if (searchedUser == null || searchedUser.getIsDeleted()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("userNotFound");
                }
            } else {
                vCode = ValidatorCollection.generateVerificationCode();
                newReservation.setCancelVCode(passwordEncoder.encode(vCode));
            }
            try {
                emailSender.sendEmailAboutReservation(newReservation.getEmail(), vCode, newReservation.getFirstName(), newReservation.getLastName());
            } catch (MessagingException ex) {
                return ResponseEntity.internalServerError().body("emailSenderError");
            }
            reservedDateRepository.save(newReservation.getReservedHours().getDate());

            newReservation.setPhone(newReservation.getPhone().trim().replaceAll(" ", ""));
            Reservations newReservations = reservationRepository.save(newReservation);
            newReservations.setReservationId(new Random().nextInt(100000, 999999) + "" + newReservations.getId());
            reservationRepository.save(newReservations);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //Foglalas lemondasa
    public ResponseEntity<Object> cancelReservation(Long id, Users canceledBy) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            Reservations searchedReservation = reservationRepository.findById(id).orElse(null);

            if (searchedReservation == null || searchedReservation.getIsCanceled()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("reservationNotFound");
            } else {
                if (canceledBy == null) {
                    searchedReservation.setCancelerEmail(searchedReservation.getEmail());
                } else {
                    Users searchedUser = userRepository.findById(canceledBy.getId()).orElse(null);
                    if (searchedUser == null || searchedUser.getIsDeleted()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("userNotFound");
                    } else {
                        searchedReservation.setCanceledBy(canceledBy);
                    }
                }
                ReservedHours searchedReservedHour = searchedReservation.getReservedHours();
                searchedReservedHour.setIsDeleted(true);
                searchedReservedHour.setDeletedAt(new Date());
                reservedHoursRepository.save(searchedReservedHour);

                searchedReservation.setIsCanceled(true);
                searchedReservation.setCanceledAt(new Date());
                searchedReservation.setStatus(statusRepository.findById(3).get());
                emailSender.sendEmailAboutReservationCanceled(searchedReservation.getEmail());
                return ResponseEntity.ok(reservationRepository.save(searchedReservation));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Object> getReservationByEmailAndVCode(String email, String vCode) {
        try {
            if (email == null || vCode == null) {
                return ResponseEntity.status(422).build();
            }

            if (ValidatorCollection.emailChecker(email)) {
                List<String> allEmail = reservationRepository.getAllReservationEmail();
                if (!allEmail.contains(email)) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("emailNotFound");
                } else {
                    List<Reservations> reservationsList = reservationRepository.getReservationsByEmail(email);
                    Reservations wantedReservation = reservationsList.stream().filter(reservation -> passwordEncoder.matches(vCode, reservation.getCancelVCode())).toList().getFirst();

                    if (wantedReservation == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("reservationNotFound");
                    } else {
                        return ResponseEntity.ok(wantedReservation);
                    }
                }
            } else {
                return ResponseEntity.status(415).body("invalidEmail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<ReservedDates> getReservedDateByDate(String selectedDateText) {
        try {
            if (selectedDateText == null) {
                return ResponseEntity.status(422).build();
            }

            ReservedDates reservedDate = reservedDateRepository.getReservedDateByDate(LocalDate.parse(selectedDateText)).orElse(new ReservedDates());
            return ResponseEntity.ok().body(reservedDate);

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