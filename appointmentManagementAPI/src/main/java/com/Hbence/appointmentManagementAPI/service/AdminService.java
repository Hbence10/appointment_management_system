package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.repository.AdminDetailsRepository;
import com.Hbence.appointmentManagementAPI.repository.ReservationRepository;
import com.Hbence.appointmentManagementAPI.repository.ReservedDateRepository;
import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import com.Hbence.appointmentManagementAPI.service.other.ValidatorCollection;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminDetailsRepository adminDetailsRepository;
    private final ReservedDateRepository reservedDateRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ArrayList<String> closeTypes = new ArrayList<String>(Arrays.asList("holiday", "full", "other"));
    private final ArrayList<String> days = new ArrayList<>(Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"));

    //ADMIN FOGLALAS
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

    //FOGLALASOK VISSZASZERZESE AZ ADMIN FOGLALASHOZ
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

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<Object> checkReservationForSimple(String dateText, Integer startHour, Integer endHour){
         if (startHour > endHour) {
            return null;
        } else {
            List<Long> idList = reservationRepository.checkReservationForAdminReservation(LocalDate.parse(dateText), startHour, endHour);
            return ResponseEntity.ok().body(reservationRepository.findAllById(idList));
        }
    }

    //FOGLALASOK VISSZASZERZESE REPETITIVE ZARASHOZ
    public ResponseEntity<Object> repetitiveCloseCheck(String startDateText, String endDateText, ArrayList<String> selectedDays){
        if (ValidatorCollection.rangeValidator(startDateText, endDateText)) {
            return ResponseEntity.status(417).build();
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
    }

    //ADMINOK KEZELESE
    @PreAuthorize("hasRole('superAdmin')")
    public ResponseEntity<Users> makeAdmin(Long userId, AdminDetails details) {
        Users searchedUser = userRepository.findById(userId).get();

        if (searchedUser.getId() == null || searchedUser.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else if (!ValidatorCollection.emailChecker(details.getEmail())) {
            return ResponseEntity.status(407).build();
        } else if (details.getId() != null) {
            return ResponseEntity.internalServerError().build();
        } else {
            searchedUser.setRole(new Role(Long.valueOf("2"), "ROLE_admin"));
            details.setAdminUser(searchedUser);
            adminDetailsRepository.save(details);
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
}
