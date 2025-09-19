package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.repository.*;
import com.Hbence.appointmentManagementAPI.service.other.ReservedDatesWithHour;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Transactional
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationTypeRepository reservationTypeRepository;
    private final ReservedDateRepository reservedDateRepository;
    private final ReservedHoursRepository reservedHoursRepository;
    private final ObjectMapper objectMapper;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

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
        for(ReservedDates i : reservedDatesList){
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
        if(emailChecker(newReservation.getEmail())){
            return ResponseEntity.status(417).body("InvalidEmail");
        }

        reservedDateRepository.save(newReservation.getReservedHours().getDate());

        return ResponseEntity.ok(reservationRepository.save(newReservation));
    }

    public ResponseEntity<Reservations> cancelReservation(Long id, Map<String, Object> cancelBody) {
        Reservations baseReservation = reservationRepository.findById(id).get();

        Reservations patchedReservation = setPatchedLikeDetails(cancelBody, baseReservation);
        patchedReservation.setCanceledAt(LocalDate.now());

        return ResponseEntity.ok(reservationRepository.save(patchedReservation));
    }

    //Foglalasi tipusok
    public ResponseEntity<List<ReservationType>> getAllReservationType() {
        return ResponseEntity.ok(reservationTypeRepository.findAll());
    }

    public ResponseEntity<ReservationType> addNewReservationType(ReservationType newReservationType) {
        return null;
    }

    public ResponseEntity<String> deleteReservationType(Long id) {
        return null;
    }

    public ResponseEntity<ReservationType> updateReservationType(ReservationType updatedReservationType) {
        return null;
    }

    //Fizetesi modszerek
    public ResponseEntity<List<PaymentMethods>> getAllPaymentMethod() {
        return ResponseEntity.ok(paymentMethodRepository.findAll());
    }

    //----------------------------------------
    //Egyéb
    private Reservations setPatchedLikeDetails(Map<String, Object> cancelBody, Reservations baseReservation) {
        ObjectNode baseReservationNode = objectMapper.convertValue(baseReservation, ObjectNode.class);
        ObjectNode cancelDetailsNode = objectMapper.convertValue(cancelBody, ObjectNode.class);

        baseReservationNode.setAll(cancelDetailsNode);

        return objectMapper.convertValue(baseReservationNode, Reservations.class);
    }

    public List<ReservedHours> asd() {
        return reservedHoursRepository.findAll();
    }

    public static boolean emailChecker(String email) {
        if (email == null || email.length() > 100) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}

