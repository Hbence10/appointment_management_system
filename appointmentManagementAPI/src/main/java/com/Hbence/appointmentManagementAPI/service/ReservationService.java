package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.configurations.emailSender.EmailSender;
import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.repository.*;
import com.Hbence.appointmentManagementAPI.service.other.ValidatorCollection;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservedDateRepository reservedDateRepository;
    private final ReservedHoursRepository reservedHoursRepository;
    private final AdminDetailsRepository adminDetailsRepository;
    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<List<Reservations>> getReservationByUserId(Long userId) {
        return ResponseEntity.ok(reservationRepository.reservations(userId));
    }

    public ResponseEntity<Object> getReservationBetweenIntervallum(String startDateText, String endDateText) {
        if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
            return ResponseEntity.status(417).build();
        }

        List<ReservedDates> reservedDatesList = reservedDateRepository.reservedDatesByDate(LocalDate.parse(startDateText), LocalDate.parse(endDateText));

        return ResponseEntity.ok(reservedDatesList);
    }

    public ResponseEntity<List<ReservedHours>> getReservedHoursByDay(String wantedDayDate) {
        List<ReservedHours> reservedHoursList = reservedHoursRepository.findAllById(reservedHoursRepository.getReservationByMonth(LocalDate.parse(wantedDayDate)));
        return ResponseEntity.ok(reservedHoursList);
    }

    public ResponseEntity<List<Reservations>> getReservationByDate(String wantedDate) {
        List<Long> idList = reservationRepository.getReservationByDate(LocalDate.parse(wantedDate));
        List<Reservations> reservationsList = reservationRepository.findAllById(idList);

        return ResponseEntity.ok(reservationsList);
    }

    //Egyszeru foglalas
    public ResponseEntity<Object> makeReservation(Reservations newReservation) {
        String vCode = "";
        if (!ValidatorCollection.emailChecker(newReservation.getEmail())) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else if (!ValidatorCollection.phoneValidator(newReservation.getPhone())) {
            return ResponseEntity.status(417).body("InvalidPhoneNumber");
        }

        if (newReservation.getUser() != null) {
            Users searchedUser = userRepository.findById(newReservation.getUser().getId()).get();
            if (searchedUser.getId() == null) {
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
        return ResponseEntity.ok(reservationRepository.save(newReservation));
    }

    //Foglalas lemondasa
    public ResponseEntity<Reservations> cancelReservation(Long id, Users canceledBy) {
        Reservations searchedReservation = reservationRepository.findById(id).get();

        if (searchedReservation.getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            if (canceledBy.getId() == null) {
                searchedReservation.setCancelerEmail(searchedReservation.getEmail());
            } else {
                Users searchedUser = userRepository.findById(canceledBy.getId()).get();
                if (searchedUser.getId() == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    searchedReservation.setCanceledBy(canceledBy);
                }
            }
            searchedReservation.setIsCanceled(true);
            searchedReservation.setCanceledAt(LocalDate.now());
            searchedReservation.setStatus(new Status(Long.valueOf("3"), "Lemondott"));
            emailSender.sendEmailAboutReservationCanceled(searchedReservation.getEmail());
            return ResponseEntity.ok(reservationRepository.save(searchedReservation));
        }
    }

    public ResponseEntity<Object> getReservationByEmailAndVCode(String email, String vCode) {
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
            return ResponseEntity.status(409).body("InvalidEmail");
        }
    }

    //ADMIN PAGE
    //Tovabbi foglalas az admin pagen:


    public ResponseEntity<ReservedDates> getReservedDateByDate(String selectedDateText) {
        ReservedDates reservedDate = reservedDateRepository.getReservedDateByDate(LocalDate.parse(selectedDateText));

        if (reservedDate == null) {
            return ResponseEntity.ok().body(new ReservedDates());
        } else {
            return ResponseEntity.ok().body(reservedDate);
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<List<Reservations>> getReservationForAdmin(String startDateText, String endDateText, int startHour, int endHour) {
        if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
            return ResponseEntity.notFound().build();
        } else {
            return null;
        }

    }


}

