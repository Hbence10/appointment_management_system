package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.repository.ReservationRepository;
import com.Hbence.appointmentManagementAPI.repository.ReservedDateRepository;
import com.Hbence.appointmentManagementAPI.repository.ReservedHoursRepository;
import com.Hbence.appointmentManagementAPI.service.other.ReservedDatesWithHour;
import com.Hbence.appointmentManagementAPI.service.other.ValidatorCollection;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ReservationService {

//    private final PaymentMethodRepository paymentMethodRepository;
    private final ReservationRepository reservationRepository;
//    private final ReservationTypeRepository reservationTypeRepository;
    private final ReservedDateRepository reservedDateRepository;
    private final ReservedHoursRepository reservedHoursRepository;
//    private final PhoneCountryCodeRepository phoneCountryCodeRepository;

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

    public ResponseEntity<Reservations> cancelReservation(Long id, Users canceledBy) {
        Reservations searchedReservation = reservationRepository.findById(id).get();

        if (searchedReservation.getId() == null){
            return ResponseEntity.notFound().build();
        } else {
            searchedReservation.setCanceledBy(canceledBy);
            searchedReservation.setIsCanceled(true);
            searchedReservation.setCanceledAt(LocalDate.now());
            searchedReservation.setStatus(new Status(Long.valueOf("3"), "Lemondott"));
            return ResponseEntity.ok(reservationRepository.save(searchedReservation));
        }
    }

    //Foglalasi tipusok


    //----------------------------------------
    //Egyéb
    public Boolean phoneValidator(String phoneNumber) {
        ArrayList<String> phoneServiceCodes = new ArrayList<String>(Arrays.asList("30", "20", "70", "50", "31"));
        return phoneServiceCodes.contains(phoneNumber.substring(0, 2)) && phoneNumber.length() == 9;
        //https://hu.wikipedia.org/wiki/Magyar_mobilszolg%C3%A1ltat%C3%B3k
    }
}

