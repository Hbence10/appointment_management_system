package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.repository.*;
import com.Hbence.appointmentManagementAPI.service.other.ValidatorCollection;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminDetailsRepository adminDetailsRepository;
    private final ReservedDateRepository reservedDateRepository;
    private final ReservationRepository reservationRepository;
    private final CloseReasonRepository closeReasonRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ArrayList<String> days = new ArrayList<>(Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"));

    //ADMIN FOGLALAS
    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> makeAdminReservation(Long adminId, Integer startHour, Integer endHour, String dateText) {
        try {
            if (adminId == null || startHour == null || endHour == null || dateText == null) {
                return ResponseEntity.status(422).build();
            }

            AdminDetails searchedAminDetails = adminDetailsRepository.findById(adminId).orElse(null);

            if (searchedAminDetails == null || searchedAminDetails.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (startHour >= endHour) {
                return ResponseEntity.status(415).build();
            } else {
                Reservations baseReservation = new Reservations();
                baseReservation = setAdminDetails(baseReservation, searchedAminDetails);
                ReservedDates reservedDates = reservedDateRepository.getReservedDateByDate(LocalDate.parse(dateText)).orElse(null);
                ReservedHours reservedHours = new ReservedHours(startHour, endHour);

                if (reservedDates == null || reservedDates.getId() == null) {
                    reservedHours.setDate(new ReservedDates(LocalDate.parse(dateText)));
                } else {
                    reservedHours.setDate(reservedDates);
                }

                baseReservation.setReservedHours(reservedHours);

                reservedDateRepository.save(baseReservation.getReservedHours().getDate());
                reservationRepository.save(baseReservation);

                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> makeReservationBetweenPeriod(String startDateText, String endDateText, Integer startHour, Integer endHour, Long adminId) {
        try {
            if (startDateText == null || endDateText == null || startHour == null || endHour == null || adminId == null) {
                return ResponseEntity.status(422).build();
            }

            AdminDetails searchedAminDetails = adminDetailsRepository.findById(adminId).orElse(null);

            if (searchedAminDetails == null || searchedAminDetails.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
                return ResponseEntity.status(415).body("invalidDateRange");
            } else if (startHour >= endHour) {
                return ResponseEntity.status(415).body("invalidHourRange");
            } else {
                List<LocalDate> dateList = LocalDate.parse(startDateText).datesUntil(LocalDate.parse(endDateText)).toList();
                for (int i = 0; i < dateList.size(); i++) {
                    Reservations baseReservation = new Reservations();
                    baseReservation = setAdminDetails(baseReservation, searchedAminDetails);

                    ReservedDates reservedDates = reservedDateRepository.getReservedDateByDate(dateList.get(i)).orElse(null);
                    ReservedHours reservedHours = new ReservedHours(startHour, endHour);

                    if (reservedDates == null || reservedDates.getId() == null) {
                        reservedHours.setDate(new ReservedDates(dateList.get(i)));
                    } else {
                        reservedHours.setDate(reservedDates);
                    }

                    baseReservation.setReservedHours(reservedHours);

                    reservedDateRepository.save(baseReservation.getReservedHours().getDate());
                    reservationRepository.save(baseReservation);
                }

                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> makeReservationByRepetitiveDates(String startDateText, String endDateText, ArrayList<String> selectedDays, Integer startHour, Integer endHour, Long adminId) {
        try {
            if (startDateText == null || endDateText == null || selectedDays == null || startHour == null || endHour == null || adminId == null) {
                return ResponseEntity.status(422).build();
            }

            AdminDetails searchedAminDetails = adminDetailsRepository.findById(adminId).orElse(null);
            if (searchedAminDetails == null || searchedAminDetails.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
                return ResponseEntity.status(415).body("invalidDateRange");
            } else if (startHour >= endHour) {
                return ResponseEntity.status(415).body("invalidHourRange");
            } else {
                List<LocalDate> dateList = LocalDate.parse(startDateText).datesUntil(LocalDate.parse(endDateText)).toList();
                ArrayList<Reservations> createdReservations = new ArrayList<>();

                for (int i = 0; i < dateList.size(); i++) {
                    if (selectedDays.contains(dateList.get(i).getDayOfWeek().toString())) {
                        Reservations baseReservation = new Reservations();
                        baseReservation = setAdminDetails(baseReservation, searchedAminDetails);

                        ReservedDates reservedDates = reservedDateRepository.getReservedDateByDate(dateList.get(i)).orElse(null);
                        ReservedHours reservedHours = new ReservedHours(startHour, endHour);

                        if (reservedDates == null || reservedDates.getId() == null) {
                            reservedHours.setDate(new ReservedDates(dateList.get(i)));
                        } else {
                            reservedHours.setDate(reservedDates);
                        }

                        baseReservation.setReservedHours(reservedHours);
                        reservedDateRepository.save(baseReservation.getReservedHours().getDate());
                        createdReservations.add(baseReservation);
                    }
                }

                reservationRepository.saveAll(createdReservations);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    public Reservations setAdminDetails(Reservations newReservation, AdminDetails adminDetails) {
        newReservation.setFirstName(adminDetails.getFirstName());
        newReservation.setLastName(adminDetails.getLastName());
        newReservation.setEmail(adminDetails.getEmail());
        newReservation.setPhone(adminDetails.getPhone());
        newReservation.setUser(adminDetails.getAdminUser());
        newReservation.setPhoneCountryCode(new PhoneCountryCode(Long.valueOf("102"), 36, "Hungary"));
        return newReservation;
    }

    //TEREM BEZARASA
    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Object> closeRoomForADay(String selectedDateText, Integer closeReasonId) {
        try {
            if (selectedDateText == null || closeReasonId == null) {
                return ResponseEntity.status(422).build();
            }

            CloseReason searchedCloseReason = closeReasonRepository.findById(closeReasonId).orElse(null);

            if (searchedCloseReason == null || searchedCloseReason.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                ReservedDates selectedDate = reservedDateRepository.getReservedDateByDate(LocalDate.parse(selectedDateText)).orElse(null);
                if (selectedDate == null || selectedDate.getId() == null) {
                    selectedDate = new ReservedDates(LocalDate.parse(selectedDateText), searchedCloseReason);
                } else {
                    selectedDate.setCloseReason(searchedCloseReason);
                }
                reservedDateRepository.save(selectedDate);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Object> closeRoomBetweenPeriod(String startDateText, String endDateText, Integer closeReasonId) {
        try {
            if (startDateText == null || endDateText == null || closeReasonId == null) {
                return ResponseEntity.status(422).build();
            }

            CloseReason searchedCloseReason = closeReasonRepository.findById(closeReasonId).orElse(null);

            if (searchedCloseReason == null || searchedCloseReason.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
                return ResponseEntity.status(415).body("invalidDateRange");
            } else {
                List<LocalDate> dateList = LocalDate.parse(startDateText).datesUntil(LocalDate.parse(endDateText)).toList();
                List<ReservedDates> closedDates = new ArrayList<ReservedDates>();

                for (int i = 0; i < dateList.size(); i++) {
                    ReservedDates searchedDate = reservedDateRepository.getReservedDateByDate(dateList.get(i)).orElse(null);

                    if (searchedDate == null || searchedDate.getId() == null) {
                        searchedDate = new ReservedDates(dateList.get(i), searchedCloseReason);
                    } else {
                        searchedDate.setCloseReason(searchedCloseReason);
                    }
                    closedDates.add(searchedDate);
                }
                reservedDateRepository.saveAll(closedDates);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Object> closeByRepetitiveDates(String startDateText, String endDateText, Integer closeReasonId, ArrayList<String> selectedDays) {
        try {
            if (startDateText == null || endDateText == null || closeReasonId == null || selectedDays == null) {
                return ResponseEntity.status(422).build();
            }

            CloseReason searchedCloseReason = closeReasonRepository.findById(closeReasonId).orElse(null);

            if (searchedCloseReason == null || searchedCloseReason.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
                return ResponseEntity.status(415).body("invalidDateRange");
            } else {
                List<LocalDate> dateList = LocalDate.parse(startDateText).datesUntil(LocalDate.parse(endDateText)).toList();
                List<ReservedDates> closedDates = new ArrayList<>();

                for (int i = 0; i < dateList.size(); i++) {
                    if (selectedDays.contains(dateList.get(i).getDayOfWeek().toString())) {
                        ReservedDates searchedDate = reservedDateRepository.getReservedDateByDate(dateList.get(i)).orElse(null);

                        if (searchedDate == null || searchedDate.getId() == null) {
                            searchedDate = new ReservedDates(dateList.get(i), searchedCloseReason);
                        } else {
                            searchedDate.setCloseReason(searchedCloseReason);
                        }

                        closedDates.add(searchedDate);
                    }
                }
                reservedDateRepository.saveAll(closedDates);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //CLOSEREASON
    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<List<CloseReason>> getAllCloseReason() {
        try {
            return ResponseEntity.ok().body(closeReasonRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Object> addCloseReason(CloseReason newCloseReason) {
        try {
            if (newCloseReason == null) {
                return ResponseEntity.status(422).build();
            }

            if (newCloseReason.getId() != null) {
                return ResponseEntity.status(415).build();
            }

            return ResponseEntity.ok().body(closeReasonRepository.save(newCloseReason));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //FOGLALASOK VISSZASZERZESE AZ ADMIN FOGLALASHOZ
    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> getReservationsForAdminIntervallum(String startDateText, String endDateText, Integer startHour, Integer endHour) {
        try {
            if (startDateText == null || endDateText == null || startHour == null || endHour == null) {
                return ResponseEntity.status(422).build();
            }

            if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
                return ResponseEntity.status(415).body("invalidDateRange");
            } else if (startHour >= endHour) {
                return ResponseEntity.status(415).body("invalidHourRange");
            } else {
                List<Long> idList = reservationRepository.getReservationsForAdminReservation(LocalDate.parse(startDateText), LocalDate.parse(endDateText), startHour, endHour);
                return ResponseEntity.ok().body(reservationRepository.findAllById(idList));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> checkReservationForRepetitive(String startDateText, String endDateText, List<String> selectedDays, Integer startHour, Integer endHour) {
        try {
            if (startDateText == null || endDateText == null || selectedDays == null || startHour == null || endHour == null) {
                return ResponseEntity.status(422).build();
            }

            if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
                return ResponseEntity.status(415).body("invalidDateRange");
            } else if (startHour >= endHour) {
                return ResponseEntity.status(415).body("invalidHourRange");
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
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> checkReservationForSimple(String dateText, Integer startHour, Integer endHour) {
        try {
            if (dateText == null || startHour == null || endHour == null) {
                return ResponseEntity.status(422).build();
            }

            if (startHour >= endHour) {
                return ResponseEntity.status(415).body("invalidHourRange");
            } else {
                List<Long> idList = reservationRepository.checkReservationForAdminReservation(LocalDate.parse(dateText), startHour, endHour);
                return ResponseEntity.ok().body(reservationRepository.findAllById(idList));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //FOGLALASOK VISSZASZERZESE A ZARASHOZ
    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> intervallumCloseCheck(String startDateText, String endDateText) {
        try {
            if (startDateText == null || endDateText == null) {
                return ResponseEntity.status(422).build();
            }

            if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
                return ResponseEntity.status(415).body("invalidDateRange");
            } else {
                List<Long> idList = reservationRepository.getAllReservationsBetweenIntervallum(LocalDate.parse(startDateText), LocalDate.parse(endDateText));
                return ResponseEntity.ok().body(reservationRepository.findAllById(idList));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> repetitiveCloseCheck(String startDateText, String endDateText, ArrayList<String> selectedDays) {
        try {
            if (startDateText == null || endDateText == null || selectedDays == null) {
                return ResponseEntity.status(422).build();
            }

            if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
                return ResponseEntity.status(415).body("invalidDateRange");
            } else {
                List<LocalDate> dateList = LocalDate.parse(startDateText).datesUntil(LocalDate.parse(endDateText)).toList();
                List<Long> idList = new ArrayList<>();

                for (int i = 0; i < dateList.size(); i++) {
                    if (selectedDays.contains(dateList.get(i).getDayOfWeek().toString())) {
                        idList.addAll(reservationRepository.getReservationByDate(dateList.get(i)));
                    }
                }
                return ResponseEntity.ok().body(reservationRepository.findAllById(idList));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //ADMINOK KEZELESE
    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Object> makeAdmin(Long userId, AdminDetails details) {
        try {
            if (userId == null || details == null) {
                return ResponseEntity.status(422).build();
            }

            Users searchedUser = userRepository.findById(userId).orElse(null);

            if (searchedUser == null || searchedUser.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (!ValidatorCollection.emailChecker(details.getEmail())) {
                return ResponseEntity.status(415).body("invalidEmail");
            } else if (details.getId() != null) {
                return ResponseEntity.notFound().build();
            } else {
                searchedUser.setRole(roleRepository.findById(2).get());
                details.setAdminUser(searchedUser);
                adminDetailsRepository.save(details);
                return ResponseEntity.ok(userRepository.save(searchedUser));
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<List<Users>> getAllAdmin() {
        try {
            return ResponseEntity.ok().body(userRepository.getAllAdmin());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Object> updateAdmin(AdminDetails updatedAdminDetails) {
        try {
            if (updatedAdminDetails == null) {
                return ResponseEntity.status(422).build();
            }

            AdminDetails testDetails = adminDetailsRepository.findById(updatedAdminDetails.getId()).orElse(null);

            if (testDetails == null || testDetails.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else if (!ValidatorCollection.emailChecker(updatedAdminDetails.getEmail())) {
                return ResponseEntity.status(415).build();
            } else {
                return ResponseEntity.ok().body(adminDetailsRepository.save(updatedAdminDetails));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Object> deleteAdmin(Long id) {
        try {
            if (id == null) {
                return ResponseEntity.status(422).build();
            }

            AdminDetails searchedAdminDetails = adminDetailsRepository.findById(id).orElse(null);
            if (searchedAdminDetails == null || searchedAdminDetails.getIsDeleted()) {
                return ResponseEntity.notFound().build();
            } else {
                searchedAdminDetails.setIsDeleted(true);
                searchedAdminDetails.setDeletedAt(new Date());
                adminDetailsRepository.save(searchedAdminDetails);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Object> getShortUsersList() {
        try {
            List<Users> userList = userRepository.findAll().stream().filter(user -> user.getRole().getId() == 1 && !user.getIsDeleted()).toList();
            List<Map<String, Object>> responseList = new ArrayList<>();

            for (int i = 0; i < userList.size(); i++) {
                Map<String, Object> eachResponse = new HashMap<>();
                eachResponse.put("id", userList.get(i).getId());
                eachResponse.put("username", userList.get(i).getUsername());
                responseList.add(eachResponse);
            }

            return ResponseEntity.ok().body(responseList);
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