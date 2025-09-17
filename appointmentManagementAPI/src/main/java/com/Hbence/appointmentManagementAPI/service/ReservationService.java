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
    public ResponseEntity<List<Reservations>> getReservationByUserId(Long userId) {
        return ResponseEntity.ok(reservationRepository.reservations(userId));
    }

    public ResponseEntity<List<ReservedDates>> getReservationByMonth(String startDate, String endDate) {
        List<ReservedDates> reservedDatesList = reservedDateRepository.reservedDatesByDate(LocalDate.parse(startDate), LocalDate.parse(endDate));
        return ResponseEntity.ok(reservedDatesList);
    }

    public ResponseEntity<List<ReservedHours>> getReservedHoursByDay(String wantedDayDate) {
        List<ReservedHours> reservedHoursList = reservedHoursRepository.findAllById(reservedHoursRepository.getReservationByMonth(LocalDate.parse(wantedDayDate)));
        return ResponseEntity.ok(reservedHoursList);
    }

    public ResponseEntity<List<Reservations>> getReservationByDate(String wantedDate) {
        List<Reservations> reservationsList = reservationRepository.getReservationByDate(LocalDate.parse(wantedDate));
        return ResponseEntity.ok(reservationsList);
    }

    public ResponseEntity<Reservations> makeReservation(Reservations newReservation) {
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

    public ResponseEntity<ReservationType> addNewReservationType(ReservationType newReservationType){
        return null;
    }

    public ResponseEntity<String> deleteReservationType(Long id){
        return null;
    }

    public ResponseEntity<ReservationType> updateReservationType(ReservationType updatedReservationType){
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

    public List<Reservations> asd(){
        return reservationRepository.findAll();
    }
}

