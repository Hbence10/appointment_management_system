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
    private final ArrayList<String> closeTypes = new ArrayList<String>(Arrays.asList("holiday", "full", "other"));
    private final ArrayList<String> days = new ArrayList<>(Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"));

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
    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> makeAdminReservation(ReservedHours selectedHour, Long adminId) {
        AdminDetails searchedAminDetails = adminDetailsRepository.findById(adminId).get();

        if (searchedAminDetails.getId() == null) {
            return ResponseEntity.notFound().build();
        } else if (searchedAminDetails.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            Reservations baseReservation = new Reservations();
            baseReservation = setAdminDetails(baseReservation, searchedAminDetails);
            baseReservation.setReservedHours(selectedHour);

            reservedDateRepository.save(baseReservation.getReservedHours().getDate());
            reservationRepository.save(baseReservation);

            return ResponseEntity.ok().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> makeReservationBetweenPeriod(String startDateText, String endDateText, ReservedHours selectedHour, Long adminId) {
        AdminDetails searchedAminDetails = adminDetailsRepository.findById(adminId).get();

        if (searchedAminDetails.getId() == null || searchedAminDetails.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
            return ResponseEntity.notFound().build();
        } else {
            List<LocalDate> dateList = LocalDate.parse(startDateText).datesUntil(LocalDate.parse(endDateText)).toList();
            for (int i = 0; i < dateList.size(); i++) {
                selectedHour.setDate(new ReservedDates(dateList.get(i)));
                Reservations baseReservation = new Reservations();
                baseReservation = setAdminDetails(baseReservation, searchedAminDetails);
                baseReservation.setReservedHours(selectedHour);

                reservedDateRepository.save(baseReservation.getReservedHours().getDate());
                reservationRepository.save(baseReservation);
            }

            return ResponseEntity.ok().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> makeReservationByRepetitiveDates(String startDateText, String endDateText, ArrayList<String> selectedDays, ReservedHours repetitiveHour, Long adminId) {
        AdminDetails searchedAminDetails = adminDetailsRepository.findById(adminId).get();
        if (searchedAminDetails.getId() == null || searchedAminDetails.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
            return ResponseEntity.notFound().build();
        } else {
            List<LocalDate> dateList = LocalDate.parse(startDateText).datesUntil(LocalDate.parse(endDateText)).toList();
            ArrayList<Reservations> createdReservations = new ArrayList<>();

            for (int i = 0; i < dateList.size(); i++) {
                if (selectedDays.contains(dateList.get(i).getDayOfWeek().toString())) {
                    Reservations baseReservation = new Reservations();
                    repetitiveHour.setDate(new ReservedDates(dateList.get(i)));
                    baseReservation = setAdminDetails(baseReservation, searchedAminDetails);
                    reservedDateRepository.save(baseReservation.getReservedHours().getDate());
                    createdReservations.add(baseReservation);
                }
            }

            reservationRepository.saveAll(createdReservations);
            return ResponseEntity.ok().build();
        }
    }

    //Terem bezárása
    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Object> closeRoomForADay(String selectedDateText, String closeType) {
        if (!closeTypes.contains(closeType)) {
            return ResponseEntity.status(409).build();
        } else {
            ReservedDates selectedDate = reservedDateRepository.getReservedDateByDate(LocalDate.parse(selectedDateText));
            if (selectedDate == null || selectedDate.getId() == null) {
                selectedDate = new ReservedDates(LocalDate.parse(selectedDateText), closeType.equals("holiday"), closeType.equals("full"), closeType.equals("other"));
                System.out.println("nincs ilyen datum");
            } else {
                selectedDate.setIsHoliday(closeType.equals("holiday"));
                selectedDate.setIsFull(closeType.equals("full"));
                selectedDate.setIsClosed(closeType.equals("other"));
                System.out.println("van ilyen datum");
            }
            System.out.println(selectedDate.getDate());
            reservedDateRepository.save(selectedDate);
            return ResponseEntity.ok().build();
        }
    }

    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Object> closeRoomBetweenPeriod(String startDateText, String endDateText, String closeType) {
        if (!closeTypes.contains(closeType)) {
            return ResponseEntity.status(409).build();
        } else if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
            return ResponseEntity.status(417).build();
        } else {
            List<LocalDate> dateList = LocalDate.parse(startDateText).datesUntil(LocalDate.parse(endDateText)).toList();
            List<ReservedDates> closedDates = new ArrayList<ReservedDates>();

            for (int i = 0; i < dateList.size(); i++) {
                ReservedDates searchedDate = reservedDateRepository.getReservedDateByDate(dateList.get(i));

                if (searchedDate == null || searchedDate.getId() == null) {
                    searchedDate = new ReservedDates(dateList.get(i), closeType.equals("holiday"), closeType.equals("full"), closeType.equals("other"));
                } else {
                    searchedDate.setIsHoliday(closeType.equals("holiday"));
                    searchedDate.setIsFull(closeType.equals("full"));
                    searchedDate.setIsClosed(closeType.equals("other"));
                }
                closedDates.add(searchedDate);
            }
            reservedDateRepository.saveAll(closedDates);
            return ResponseEntity.ok().build();
        }
    }

    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Object> closeByRepetitiveDates(String startDateText, String endDateText, String closeType, ArrayList<String> selectedDays) {
        if (!closeTypes.contains(closeType)) {
            return ResponseEntity.status(409).build();
        } else if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
            return ResponseEntity.status(417).build();
        } else {
            List<LocalDate> dateList = LocalDate.parse(startDateText).datesUntil(LocalDate.parse(endDateText)).toList();
            List<ReservedDates> closedDates = new ArrayList<>();

            for (int i = 0; i < dateList.size(); i++) {
                if (selectedDays.contains(dateList.get(i).getDayOfWeek().toString())) {
                    System.out.println(dateList.get(i));
                    ReservedDates searchedDate = reservedDateRepository.getReservedDateByDate(dateList.get(i));

                    if (searchedDate == null || searchedDate.getId() == null) {
                        searchedDate = new ReservedDates(dateList.get(i), closeType.equals("holiday"), closeType.equals("full"), closeType.equals("other"));
                    } else {
                        searchedDate.setIsHoliday(closeType.equals("holiday"));
                        searchedDate.setIsFull(closeType.equals("full"));
                        searchedDate.setIsClosed(closeType.equals("other"));
                    }

                    closedDates.add(searchedDate);
                }
            }
            reservedDateRepository.saveAll(closedDates);
            return ResponseEntity.ok().build();
        }
    }

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

    //Foglalasok visszaszerzese intervallumkor:
    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> getReservationsForAdminIntervallum(String startDateText, String endDateText, int startHour, int endHour) {
        if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
            return null;
        } else if (startHour > endHour) {
            return null;
        } else {
            List<Long> idList = reservationRepository.getReservationsForAdminReservation(LocalDate.parse(startDateText), LocalDate.parse(endDateText), startHour, endHour);
            return ResponseEntity.ok().body(reservationRepository.findAllById(idList));
        }
    }

    //Foglalasok visszaszerzese repetitive-kor
    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> checkReservationForRepetitive(String startDateText, String endDateText, ArrayList<String> selectedDays, Integer startHour, Integer endHour) {
        if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
            return null;
        } else if (startHour > endHour) {
            return null;
        } else {
            List<LocalDate> dateList = LocalDate.parse(startDateText).datesUntil(LocalDate.parse(endDateText)).toList();
            List<Long> idList = new ArrayList<Long>();

            for (int i = 0; i < dateList.size(); i++) {
                if (selectedDays.contains(dateList.get(i).getDayOfWeek().toString())) {
                    idList.addAll(reservationRepository.checkReservationForAdminReservation(dateList.get(i), startHour, endHour));
                }
            }

            return ResponseEntity.ok().body(reservationRepository.findAllById(idList));
        }
    }

    //Egyeb:
    public Reservations setAdminDetails(Reservations newReservation, AdminDetails adminDetails) {
        newReservation.setFirstName(adminDetails.getFirstName());
        newReservation.setLastName(adminDetails.getLastName());
        newReservation.setEmail(adminDetails.getEmail());
        newReservation.setPhone(adminDetails.getPhone());
        newReservation.setUser(adminDetails.getAdminUser());
        newReservation.setPhoneCountryCode(new PhoneCountryCode(Long.valueOf("102"), 36, "Hungary"));
        return newReservation;
    }
}

