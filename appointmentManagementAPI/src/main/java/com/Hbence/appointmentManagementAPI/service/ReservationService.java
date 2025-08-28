package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.PaymentMethods;
import com.Hbence.appointmentManagementAPI.entity.ReservationType;
import com.Hbence.appointmentManagementAPI.entity.Reservations;
import com.Hbence.appointmentManagementAPI.entity.ReservedDates;
import com.Hbence.appointmentManagementAPI.repository.PaymentMethodRepository;
import com.Hbence.appointmentManagementAPI.repository.ReservationRepository;
import com.Hbence.appointmentManagementAPI.repository.ReservationTypeRepository;
import com.Hbence.appointmentManagementAPI.repository.ReservedDateRepository;
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

    @Autowired
    public ReservationService(PaymentMethodRepository paymentMethodRepository, ReservationRepository reservationRepository, ReservationTypeRepository reservationTypeRepository, ReservedDateRepository reservedDateRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.reservationRepository = reservationRepository;
        this.reservationTypeRepository = reservationTypeRepository;
        this.reservedDateRepository = reservedDateRepository;
    }

    public List<PaymentMethods> getAllPaymentMethod(){
        return paymentMethodRepository.findAll();
    }

    public ArrayList<Reservations> getReservationByUserId(Integer userId){
        ArrayList<Long> id = reservationRepository.reservations(userId);
        ArrayList<Reservations> reservations = new ArrayList<>();

        for(Long i: id){
            reservations.add(reservationRepository.findById(i).get());
        }

        return reservations;
    }

    public String cancelReservation(int userId, int reservationId){
        return "";
    }

    public Response getReservedDatesByDate(String wantedDate){
        List<Long> ids = reservedDateRepository.reservedDatesByDate(LocalDate.parse(wantedDate));
        ArrayList<ReservedDates> reservedDates = new ArrayList<>();

        for(Long i : ids){
            reservedDates.add(reservedDateRepository.findById(i).get());
        }

        return new Response(HttpStatus.OK.value(), reservedDates, LocalDateTime.now());
    }
}
