package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.*;
import com.Hbence.appointmentManagementAPI.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class ReservationService {

    private PaymentMethodRepository paymentMethodRepository;
    private ReservationRepository reservationRepository;
    private ReservationTypeRepository reservationTypeRepository;
    private ReservedDateRepository reservedDateRepository;
    private ReservedHoursRepository reservedHoursRepository;

    @Autowired
    public ReservationService(PaymentMethodRepository paymentMethodRepository, ReservationRepository reservationRepository, ReservationTypeRepository reservationTypeRepository, ReservedDateRepository reservedDateRepository, ReservedHoursRepository reservedHoursRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.reservationRepository = reservationRepository;
        this.reservationTypeRepository = reservationTypeRepository;
        this.reservedDateRepository = reservedDateRepository;
        this.reservedHoursRepository = reservedHoursRepository;
    }

    public List<Reservations> getReservationByUserId(Integer userId) {
        return reservationRepository.reservations(userId);
    }

    public List<ReservedDates> getReservationByMonth(String actualDate) {
        return reservedDateRepository.reservedDatesByDate(LocalDate.parse(actualDate));
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

    public List<Reservations> getReservationByDate(Date wantedDate){
//        reservationRepository.getReservationByDate(wantedDate);

        return null;
    }
}
