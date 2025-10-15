package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.configurations.emailSender.EmailSender;
import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.repository.*;
import com.Hbence.appointmentManagementAPI.service.other.ReservedDatesWithHour;
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

    public ResponseEntity<Object> getReservationByMonth(String startDateText, String endDateText) {
        LocalDate startDate = LocalDate.parse(startDateText);
        LocalDate endDate = LocalDate.parse(endDateText);

        if (startDate.compareTo(endDate) == 1) {
            return ResponseEntity.status(417).body("A kezdo datum nem lehet kesobb mint a vegdatum");
        }

        List<ReservedDates> reservedDatesList = reservedDateRepository.reservedDatesByDate(startDate, endDate);
        List<ReservedDatesWithHour> returnList = new ArrayList<>();
        for (ReservedDates i : reservedDatesList) {
            returnList.add(new ReservedDatesWithHour(
                    i.getId(), i.getDate(), i.getIsHoliday(), i.getIsClosed(), i.getIsFull(), i.getReservedHours()
            ));
        }

        return ResponseEntity.ok(returnList);
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
    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> makeAdminReservation(Reservations baseReservation, Long adminId, Long userId) {
        Users searchedUser = userRepository.findById(userId).get();
        AdminDetails searchedAminDetails = adminDetailsRepository.findById(adminId).get();

        return null;
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> makeReservationByRepetitiveDates() {
        return null;
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> makeReservationAlwaysBetweenTwoDates() {
        return null;
    }

    //Terem bezárása
    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> closeRoomForADay() {
        return null;
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> closeRoomBetweenPeriod() {
        return null;
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> closeByRepetitiveDates() {
        return null;
    }

}

