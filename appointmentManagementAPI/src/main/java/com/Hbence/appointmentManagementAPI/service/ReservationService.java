package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.repository.*;
import com.Hbence.appointmentManagementAPI.service.exceptions.ExceptionType.InvalidEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Transactional
@Service
public class ReservationService {
    public static Boolean emailValidator(String email) {
        return true;
    }

    private final PaymentMethodRepository paymentMethodRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationTypeRepository reservationTypeRepository;
    private final ReservedDateRepository reservedDateRepository;
    private final ReservedHoursRepository reservedHoursRepository;

    @Autowired
    public ReservationService(PaymentMethodRepository paymentMethodRepository, ReservationRepository reservationRepository, ReservationTypeRepository reservationTypeRepository, ReservedDateRepository reservedDateRepository, ReservedHoursRepository reservedHoursRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.reservationRepository = reservationRepository;
        this.reservationTypeRepository = reservationTypeRepository;
        this.reservedDateRepository = reservedDateRepository;
        this.reservedHoursRepository = reservedHoursRepository;
    }

    public List<Reservations> getReservationByUserId(Long userId) {
        return reservationRepository.reservations(userId);
    }

    public List<ReservedDates> getReservationByMonth(String startDate, String endDate) {
        return reservedDateRepository.reservedDatesByDate(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    public List<ReservedHours> getReservedHoursByDay(String wantedDayDate) {
        return reservedHoursRepository.findAllById(reservedHoursRepository.getReservationByMonth(LocalDate.parse(wantedDayDate)));
    }

    public List<ReservationType> getAllReservationType() {
        return reservationTypeRepository.findAll();
    }

    public List<PaymentMethods> getAllPaymentMethod() {
        return paymentMethodRepository.findAll();
    }

    public List<Reservations> getReservationByDate(String wantedDate) {
        return reservationRepository.getReservationByDate(LocalDate.parse(wantedDate));
    }

    public Response makeReservation(Reservations newReservation) {
        Response response = new Response();
        reservationRepository.save(newReservation);
        return response;
    }
}

