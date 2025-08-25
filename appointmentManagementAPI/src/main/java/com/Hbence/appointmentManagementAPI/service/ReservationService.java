package com.Hbence.appointmentManagementAPI.service;

import com.Hbence.appointmentManagementAPI.entity.PaymentMethods;
import com.Hbence.appointmentManagementAPI.entity.ReservationType;
import com.Hbence.appointmentManagementAPI.entity.Reservations;
import com.Hbence.appointmentManagementAPI.repository.PaymentMethodRepository;
import com.Hbence.appointmentManagementAPI.repository.ReservationRepository;
import com.Hbence.appointmentManagementAPI.repository.ReservationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
@Service
public class ReservationService {

    private PaymentMethodRepository paymentMethodRepository;
    private ReservationRepository reservationRepository;
    private ReservationTypeRepository reservationTypeRepository;

    @Autowired
    public ReservationService(PaymentMethodRepository paymentMethodRepository, ReservationRepository reservationRepository, ReservationTypeRepository reservationTypeRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.reservationRepository = reservationRepository;
        this.reservationTypeRepository = reservationTypeRepository;
    }

    //Az osszes payment method megszerzese:
    public List<PaymentMethods> getAllPaymentMethod(){
        return paymentMethodRepository.findAll();
    }

    //Foglalas(ok) megszerzese felhasznalo szerint:
    public List<Reservations> getReservationByUserId(Integer userId){
        return null;
    }

    //Foglalas(ok) megszerzese nap szerint:
    public List<Reservations> getReservationByDay(){
        return null;
    }

    //Az osszes foglalasi tipus megszerzese:
    public List<ReservationType> getReservationTypes(){
        return null;
    }
}
