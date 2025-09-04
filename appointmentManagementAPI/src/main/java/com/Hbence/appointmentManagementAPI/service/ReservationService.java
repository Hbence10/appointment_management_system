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

    public List<Reservations> getReservationByDate(String wantedDate) {
        return reservationRepository.getReservationByDate(LocalDate.parse(wantedDate));
    }

    public Response makeReservation(Reservations newReservation) {
        Response response = new Response();

//        if (!emailValidator(String.valueOf(newReservation.get("email")))) {
//            throw new InvalidEmail("ajjaj");
//        }

//        String result = reservationRepository.makeReservation(
//                newReservation.get("firstName"),
//                newReservation.get("lastName"),
//                newReservation.get("email"),
//                newReservation.get("phoneNumber"),
//                newReservation.get("comment"),
//                newReservation.get("reservationType"),
//                newReservation.get("userId"),
//                newReservation.get("paymentMethod")
//        );
        reservationRepository.save(newReservation);

//        if (result.equals("successfully reservation")){
//            response = new Response(HttpStatus.OK.value(), result, LocalDateTime.now());
//        } else {
//
//        }

        return response;
    }
}

