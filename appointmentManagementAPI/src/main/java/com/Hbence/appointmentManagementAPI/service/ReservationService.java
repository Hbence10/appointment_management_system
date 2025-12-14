package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.configurations.emailSender.EmailSender;
import com.Hbence.appointmentManagementAPI.entity.Reservations;
import com.Hbence.appointmentManagementAPI.entity.ReservedDates;
import com.Hbence.appointmentManagementAPI.entity.ReservedHours;
import com.Hbence.appointmentManagementAPI.entity.Users;
import com.Hbence.appointmentManagementAPI.repository.*;
import com.Hbence.appointmentManagementAPI.service.other.ValidatorCollection;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Transactional
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservedDateRepository reservedDateRepository;
    private final ReservedHoursRepository reservedHoursRepository;
    private final StatusRepository statusRepository;
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
            if (!ValidatorCollection.emailChecker(newReservation.getEmail())) {
                return ResponseEntity.status(415).body("InvalidEmail");
            } else if (!ValidatorCollection.phoneValidator(newReservation.getPhone())) {
                return ResponseEntity.status(415).body("InvalidPhoneNumber");
            }

            if (newReservation.getUser() != null) {
                Users searchedUser = userRepository.findById(newReservation.getUser().getId()).orElse(null);
                if (searchedUser == null || searchedUser.getIsDeleted()) {
                    return ResponseEntity.notFound().build();
                }
            } else {
                vCode = ValidatorCollection.generateVerificationCode();
                newReservation.setCancelVCode(passwordEncoder.encode(vCode));
            }
            try {
                emailSender.sendEmailAboutReservation(newReservation.getEmail(), vCode, newReservation.getFirstName(), newReservation.getLastName());
            } catch (MessagingException ex) {
                return ResponseEntity.internalServerError().build();
            }
            reservedDateRepository.save(newReservation.getReservedHours().getDate());

            Reservations newReservations = reservationRepository.save(newReservation);
            newReservations.setReservationId(new Random().nextInt(100000, 999999) + "" + newReservations.getId());

            return ResponseEntity.ok(reservationRepository.save(newReservations));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //Foglalas lemondasa
    public ResponseEntity<Reservations> cancelReservation(Long id, Users canceledBy) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            Reservations searchedReservation = reservationRepository.findById(id).orElse(null);

            if (searchedReservation == null || searchedReservation.getIsCanceled()) {
                return ResponseEntity.notFound().build();
            } else {
                if (canceledBy == null) {
                    searchedReservation.setCancelerEmail(searchedReservation.getEmail());
                } else {
                    Users searchedUser = userRepository.findById(canceledBy.getId()).orElse(null);
                    if (searchedUser == null || searchedUser.getIsDeleted()) {
                        return ResponseEntity.notFound().build();
                    } else {
                        searchedReservation.setCanceledBy(canceledBy);
                    }
                }
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
                    return ResponseEntity.notFound().build();
                } else {
                    List<Reservations> reservationsList = reservationRepository.getReservationsByEmail(email);
                    Reservations wantedReservation = reservationsList.stream().filter(
                            reservation -> passwordEncoder.matches(vCode, reservation.getCancelVCode())
                    ).toList().get(0);

                    if (wantedReservation == null) {
                        return ResponseEntity.notFound().build();
                    } else {
                        return ResponseEntity.ok(wantedReservation);
                    }
                }
            } else {
                return ResponseEntity.status(415).body("InvalidEmail");
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

            ReservedDates reservedDate = reservedDateRepository.getReservedDateByDate(LocalDate.parse(selectedDateText)).orElse(null);

            if (reservedDate == null) {
                return ResponseEntity.ok().body(new ReservedDates());
            } else {
                return ResponseEntity.ok().body(reservedDate);
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