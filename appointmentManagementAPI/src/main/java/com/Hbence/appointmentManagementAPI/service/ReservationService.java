package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.*;

@Transactional
@Service
public class ReservationService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationTypeRepository reservationTypeRepository;
    private final ReservedDateRepository reservedDateRepository;
    private final ReservedHoursRepository reservedHoursRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReservationService(PaymentMethodRepository paymentMethodRepository, ReservationRepository reservationRepository, ReservationTypeRepository reservationTypeRepository, ReservedDateRepository reservedDateRepository, ReservedHoursRepository reservedHoursRepository, ObjectMapper objectMapper) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.reservationRepository = reservationRepository;
        this.reservationTypeRepository = reservationTypeRepository;
        this.reservedDateRepository = reservedDateRepository;
        this.reservedHoursRepository = reservedHoursRepository;
        this.objectMapper = objectMapper;
    }

    //Foglalasok:
    public List<Reservations> getReservationByUserId(Long userId) {
        return reservationRepository.reservations(userId);
    }

    public List<ReservedDates> getReservationByMonth(String startDate, String endDate) {
        return reservedDateRepository.reservedDatesByDate(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    public List<ReservedHours> getReservedHoursByDay(String wantedDayDate) {
        return reservedHoursRepository.findAllById(reservedHoursRepository.getReservationByMonth(LocalDate.parse(wantedDayDate)));
    }

    public List<Reservations> getReservationByDate(String wantedDate) {
        return reservationRepository.getReservationByDate(LocalDate.parse(wantedDate));
    }

    public ResponseEntity<Reservations> makeReservation(Reservations newReservation) {
        reservationRepository.save(newReservation);
        return null;
    }

    public String cancelReservation(Long id, Map<String, Object> cancelBody) {
        Reservations baseReservation = reservationRepository.findById(id).get();

        Reservations patchedReservation = setPatchedLikeDetails(cancelBody, baseReservation);
        patchedReservation.setCanceledAt(LocalDate.now());

        reservationRepository.save(patchedReservation);

        return "";
    }

    //Foglalasi tipusok
    public List<ReservationType> getAllReservationType() {
        return reservationTypeRepository.findAll();
    }

    //Fizetesi modszerek
    public List<PaymentMethods> getAllPaymentMethod() {
        return paymentMethodRepository.findAll();
    }

    //----------------------------------------
    //Egyéb
    private Reservations setPatchedLikeDetails(Map<String, Object> cancelBody, Reservations baseReservation) {
        ObjectNode baseReservationNode = objectMapper.convertValue(baseReservation, ObjectNode.class);
        ObjectNode cancelDetailsNode = objectMapper.convertValue(cancelBody, ObjectNode.class);

        baseReservationNode.setAll(cancelDetailsNode);

        return objectMapper.convertValue(baseReservationNode, Reservations.class);
    }
}

