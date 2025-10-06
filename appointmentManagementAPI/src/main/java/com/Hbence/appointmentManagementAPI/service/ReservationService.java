package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.repository.*;
import com.Hbence.appointmentManagementAPI.service.other.ReservedDatesWithHour;
import com.Hbence.appointmentManagementAPI.service.other.ValidatorCollection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationTypeRepository reservationTypeRepository;
    private final ReservedDateRepository reservedDateRepository;
    private final ReservedHoursRepository reservedHoursRepository;
    private final PhoneCountryCodeRepository phoneCountryCodeRepository;
    private final ObjectMapper objectMapper;

    //Foglalasok:
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

    public ResponseEntity<Object> makeReservation(Reservations newReservation) {
        if (!ValidatorCollection.emailChecker(newReservation.getEmail())) {
            return ResponseEntity.status(417).body("InvalidEmail");
        } else if (!phoneValidator(newReservation.getPhone())) {
            return ResponseEntity.status(417).body("InvalidPhoneNumber");
        }

        reservedDateRepository.save(newReservation.getReservedHours().getDate());

        return ResponseEntity.ok(reservationRepository.save(newReservation));
    }

    public ResponseEntity<Reservations> cancelReservation(Long id, Map<String, Object> cancelBody) {
        Reservations baseReservation = reservationRepository.findById(id).get();

        Reservations patchedReservation = setCancelForReservation(cancelBody, baseReservation);
        patchedReservation.setCanceledAt(LocalDate.now());

        return ResponseEntity.ok(reservationRepository.save(patchedReservation));
    }

    //Foglalasi tipusok
    public ResponseEntity<List<ReservationType>> getAllReservationType() {
        return ResponseEntity.ok(reservationTypeRepository.findAll().stream().filter(reservationType -> !reservationType.getIsDeleted()).toList());
    }

    //    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<ReservationType> addNewReservationType(ReservationType newReservationType) {
        System.out.println(newReservationType);
        if (newReservationType.getId() != null) {
            return ResponseEntity.notFound().build();
        } else {
            newReservationType.setName(newReservationType.getName().trim());
            return ResponseEntity.ok(reservationTypeRepository.save(newReservationType));
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<String> deleteReservationType(Long id) {
        ReservationType searchedType = reservationTypeRepository.findById(id).get();

        if (searchedType == null || searchedType.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            searchedType.setIsDeleted(true);
            searchedType.setDeletedAt(new Date());
            return ResponseEntity.ok("ok");
        }
    }

    @PreAuthorize("hasAnyRole('admin', 'superAdmin')")
    public ResponseEntity<ReservationType> updateReservationType(ReservationType updatedReservationType) {
        if (updatedReservationType.getId() == null || updatedReservationType.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        } else {
            updatedReservationType.setName(updatedReservationType.getName().trim());
            return ResponseEntity.ok(reservationTypeRepository.save(updatedReservationType));
        }
    }

    //Fizetesi modszerek
    public ResponseEntity<List<PaymentMethods>> getAllPaymentMethod() {
        return ResponseEntity.ok(paymentMethodRepository.findAll());
    }

    //Telefonszam
    public ResponseEntity<List<PhoneCountryCode>> getAllPhoneCode() {
        return ResponseEntity.ok(phoneCountryCodeRepository.findAll());
    }

    //----------------------------------------
    //Egyéb
    private Reservations setCancelForReservation(Map<String, Object> cancelBody, Reservations baseReservation) {
        ObjectNode baseReservationNode = objectMapper.convertValue(baseReservation, ObjectNode.class);
        ObjectNode cancelDetailsNode = objectMapper.convertValue(cancelBody, ObjectNode.class);

        baseReservationNode.setAll(cancelDetailsNode);

        return objectMapper.convertValue(baseReservationNode, Reservations.class);
    }

    public Boolean phoneValidator(String phoneNumber) {
        ArrayList<String> phoneServiceCodes = new ArrayList<String>(Arrays.asList("30", "20", "70", "50", "31"));
        return phoneServiceCodes.contains(phoneNumber.substring(0, 2)) && phoneNumber.length() == 9;
        //https://hu.wikipedia.org/wiki/Magyar_mobilszolg%C3%A1ltat%C3%B3k
    }
}

