package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.PaymentMethods;
import com.Hbence.appointmentManagementAPI.entity.Reservations;
import com.Hbence.appointmentManagementAPI.repository.PaymentMethodRepository;
import com.Hbence.appointmentManagementAPI.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
@Service
public class ReservationService {

    private PaymentMethodRepository paymentMethodRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(PaymentMethodRepository paymentMethodRepository, ReservationRepository reservationRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<PaymentMethods> getAllPaymentMethod(){
        return paymentMethodRepository.findAll();
    }

    public List<Reservations> getReservationByUserId(Integer userId){
        return null;
    }
}
